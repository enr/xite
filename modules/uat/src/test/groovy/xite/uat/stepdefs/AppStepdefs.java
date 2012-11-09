package xite.uat.stepdefs;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.testng.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

import com.github.enr.clap.Clap;
import com.github.enr.clap.Clap.RunResult;
import com.github.enr.clap.util.ClasspathUtil;
import com.github.enr.xite.inject.XiteModule;
import com.github.enr.xite.util.FilePaths;
import com.google.inject.Module;
import com.marvinformatics.kiss.matchers.file.FileMatchers;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AppStepdefs {
    
//    private String username;
//    private String sutName;
//    private File xiteRoot;
    
    private File sutHome;
    
    private String sutOutput;
    
    private int sutExitValue;
    
    private File parentProjectDir;
    
    private File xiteWebsiteSourceDirectory;
    private File xiteWebsiteDestinationDirectory;
    
    private Process serveProcess;
    InputStream processInputStream;

    @Given("^I am the user \"([^\"]*)\"$")
    public void I_am_the_user(String name) {
        //username = name.toLowerCase();
        
        File cc = ClasspathUtil.getClasspathForClass(AppStepdefs.class);
        File modules = cc.getParentFile().getParentFile().getParentFile().getParentFile();
        String installPath = new StringBuilder(modules.getAbsolutePath()).append(File.separatorChar).append("core").append(File.separatorChar)
                .append("target").append(File.separatorChar).append("install").append(File.separatorChar).append("xite").toString();
        sutHome =  new File(installPath);
        parentProjectDir = modules.getParentFile();
        //xiteRoot = modules.getParentFile();
    }
    
    @Given("^the website was built in \"([^\"]*)\"$")
    public void the_website_was_built_in(String destinationDir) throws Throwable {
        buildWebsite(destinationDir);
    }
    
    @When("^I run xite with \"([^\"]*)\" args$")
    public void I_run_xite_with(String argsAsString) throws Exception {
        Module testModule = new XiteModule();
        String[] args = argsAsString.split("\\s");
        RunResult result = Clap.runReviewableApp(args, this.sutHome, testModule);
        this.sutExitValue = result.getExitValue();
        this.sutOutput = result.getOutput();
    }
    
    @When("^I run xite script with \"([^\"]*)\" args$")
    public void I_run_xite_script_with(String argsAsString) throws Exception {

        String xiteBin = new StringBuilder(sutHome.getAbsolutePath()).append(File.separatorChar).append("bin").append(File.separatorChar)
                .append("xite.bat").toString();
        
        String[] args = argsAsString.split("\\s");

        ProcessBuilder pb = new ProcessBuilder(xiteBin, "serve", "--root", "target/website", "--port", "9191");
        /*
        Map<String, String> env = pb.environment();
        env.put("VAR1", "myValue");
        env.remove("OTHERVAR");
        env.put("VAR2", env.get("VAR1") + "suffix");
        pb.directory(new File("myDir"));
        */
        serveProcess = pb.start();

        processInputStream = serveProcess.getInputStream();        
        Thread.sleep(4000);
    }

    
    @When("^I build the actual Xite's website$")
    public void I_build_actual_xite_website() throws Exception {
        buildWebsite("target/uat");
    }
    
    private void buildWebsite(String destinationDirectory) {
        Module testModule = new XiteModule();
        //String websitePath = new StringBuilder(parentProjectDir.getAbsolutePath()).append(File.separatorChar).append("website").toString();
        //String argsAsString = "";
        xiteWebsiteSourceDirectory = new File(parentProjectDir, "website");
        String websitePath = FilePaths.absoluteNormalized(xiteWebsiteSourceDirectory);
        xiteWebsiteDestinationDirectory = new File(destinationDirectory);
        String destinationPath = xiteWebsiteDestinationDirectory.getAbsolutePath();
        String[] args = {"build", "--source", websitePath, "--destination", destinationPath}; //argsAsString.split("\\s");
        RunResult result = Clap.runReviewableApp(args, this.sutHome, testModule);
        this.sutExitValue = result.getExitValue();
        this.sutOutput = result.getOutput();
    }

    @Then("^the output should contain exactly \"([^\"]*)\"$")
    public void the_output_should_contain_exactly(String expectedOutput) {
        assertEquals(expectedOutput, this.sutOutput);
    }

    @Then("^the output should contain \"([^\"]*)\"$")
    public void the_output_should_contain(String expectedPieceOfOutput) {
        assertThat(this.sutOutput, containsString(expectedPieceOfOutput));
    }
    
    // ^the exit status should be (\d+)$
    @Then("^it should exit with value \"([^\"]*)\"$")
    public void it_should_exit_with_value(int expectedExitValue) {
        assertEquals(expectedExitValue, this.sutExitValue);
    }
    
    @Then("^html files should be generated from markdown$")
    public void html_files_should_be_generated_from_markdown() throws Throwable {
        File markdownDirectory = new File(xiteWebsiteSourceDirectory, "markdown");
        File[] markdownFiles = markdownDirectory.listFiles();
        for (File file : markdownFiles) {
            String fileName = file.getName();
            if (fileName.endsWith(".md")) {
                String outputPath = FilePaths.join(xiteWebsiteDestinationDirectory.getAbsolutePath(), "xite", FilePaths.changeExtension(fileName, "html"));
                File outputFile = new File(outputPath);
                assertThat( outputFile, FileMatchers.isFile() );
            }
        }
    }

    @Then("^resources are copied from standard path$")
    public void resources_are_copied_from_standard_path() throws Throwable {
        File resourcesStandardDirectory = new File(xiteWebsiteSourceDirectory, "resources");
        File[] resources = resourcesStandardDirectory.listFiles();
        for (File file : resources) {
            String fileName = file.getName();
            String outputPath = FilePaths.join(xiteWebsiteDestinationDirectory.getAbsolutePath(), "xite", fileName);
            File outputFile = new File(outputPath);
            assertTrue(outputFile.exists(), outputPath);
        }
    }
    
    @Then("^the url \"([^\"]*)\" should be valid$")
    public void the_url_should_be_valid(String url) throws Throwable {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("HEAD");
        int responseCode = connection.getResponseCode();
        assertEquals("http response code", responseCode, 200); 
    }

    @Then("^I close process$")
    public void I_close_process() throws Throwable {
        System.out.println("wait for...");
        String tempOut = convertStreamToStr(processInputStream);

        System.out.println(tempOut);
        serveProcess.waitFor(); //destroy();
        //Thread.sleep(9000);
    }
    
    /*
    * To convert the InputStream to String we use the Reader.read(char[]
    * buffer) method. We iterate until the Reader return -1 which means
    * there's no more data to read. We use the StringWriter class to
    * produce the string.
    */

    public static String convertStreamToStr(InputStream is) throws IOException {

    if (is != null) {
    Writer writer = new StringWriter();

    char[] buffer = new char[1024];
    try {
    Reader reader = new BufferedReader(new InputStreamReader(is,
    "UTF-8"));
    int n;
    while ((n = reader.read(buffer)) != -1) {
    writer.write(buffer, 0, n);
    }
    } finally {
    is.close();
    }
    return writer.toString();
    }
    else {
    return "";
    }
    }
}
