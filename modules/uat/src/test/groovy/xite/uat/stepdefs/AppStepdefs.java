package xite.uat.stepdefs;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import com.github.enr.clap.Clap;
import com.github.enr.clap.Clap.RunResult;
import com.github.enr.clap.util.ClasspathUtil;
import com.github.enr.xite.inject.XiteModule;
import com.github.enr.xite.util.FilePaths;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.inject.Module;
import com.marvinformatics.kiss.matchers.file.FileMatchers;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AppStepdefs {

    private File sutHome;
    
    private String sutOutput;
    
    private int sutExitValue;
    
    private File parentProjectDir;
    
    private String websiteName;
    private File websiteSourceDirectory;
    private File websiteDestinationDirectory;

    /*
     * placeholder method, ready for user's home customization
     */
    @Given("^I am the user \"([^\"]*)\"$")
    public void I_am_the_user(String name) {
        //username = name.toLowerCase();
        File cc = ClasspathUtil.getClasspathForClass(AppStepdefs.class);
        File modules = cc.getParentFile().getParentFile().getParentFile().getParentFile();
        String installPath = new StringBuilder(modules.getAbsolutePath()).append(File.separatorChar).append("core").append(File.separatorChar)
                .append("target").append(File.separatorChar).append("install").append(File.separatorChar).append("xite").toString();
        sutHome =  new File(installPath);
        parentProjectDir = modules.getParentFile();
    }

    @When("^I build the actual Xite's website$")
    public void I_build_actual_xite_website() throws Exception {
        this.websiteName = "xite";
        buildWebsite("website", "target/uat");
    }
    
    @When("^I build website \"([^\"]*)\"$")
    public void I_build_website(String website) throws Exception {
        this.websiteName = website;
        buildWebsite("modules/uat/src/test/websites/"+website, "target/website-"+website);
    }

    private void buildWebsite(String sourceDirectory, String destinationDirectory) {
        Module testModule = new XiteModule();
        websiteSourceDirectory = new File(parentProjectDir, sourceDirectory);
        String websitePath = FilePaths.absoluteNormalized(websiteSourceDirectory);
        websiteDestinationDirectory = new File(destinationDirectory);
        String destinationPath = websiteDestinationDirectory.getAbsolutePath();
        String[] args = {"--debug", "build", "--source", websitePath, "--destination", destinationPath};
        RunResult result = Clap.runReviewableApp(args, this.sutHome, testModule);
        this.sutExitValue = result.getExitValue();
        this.sutOutput = result.getOutput();
    }
    
    @When("^I run xite with \"([^\"]*)\" args$")
    public void I_run_xite_with(String argsAsString) throws Exception {
        Module testModule = new XiteModule();
        String[] args = argsAsString.split("\\s");
        RunResult result = Clap.runReviewableApp(args, this.sutHome, testModule);
        this.sutExitValue = result.getExitValue();
        this.sutOutput = result.getOutput();
    }

    @When("^I run xite --version$")
    public void I_run_xite_version() throws Exception {
        Module testModule = new XiteModule();
        RunResult result = Clap.runReviewableApp(new String[] {"--version"}, this.sutHome, testModule);
        this.sutExitValue = result.getExitValue();
        this.sutOutput = result.getOutput();
    }
    
    @Then("^the output should contain current build version$")
    public void the_output_should_contain_current_build_version() throws Exception {
        File buildPropertiesFile = new File(parentProjectDir, "gradle.properties");
        Properties properties = new Properties();
        properties.load(new FileInputStream(buildPropertiesFile));
        String version = properties.getProperty("version");
        String expectedOutput = "Xite version "+version;
        assertEquals(expectedOutput, this.sutOutput);
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
        File markdownDirectory = new File(websiteSourceDirectory, "markdown");
        File[] markdownFiles = markdownDirectory.listFiles();
        for (File file : markdownFiles) {
            String fileName = file.getName();
            if (fileName.endsWith(".md")) {
                String outputPath = FilePaths.join(websiteDestinationDirectory.getAbsolutePath(), this.websiteName, FilePaths.changeExtension(fileName, "html"));
                File outputFile = new File(outputPath);
                assertThat( outputFile, FileMatchers.isFile() );
            }
        }
    }

    @Then("^resources are copied from standard path$")
    public void resources_are_copied_from_standard_path() throws Throwable {
        File resourcesStandardDirectory = new File(websiteSourceDirectory, "resources");
        File[] resources = resourcesStandardDirectory.listFiles();
        for (File file : resources) {
            String fileName = file.getName();
            String outputPath = FilePaths.join(websiteDestinationDirectory.getAbsolutePath(), this.websiteName, fileName);
            File outputFile = new File(outputPath);
            assertTrue(outputFile.exists(), outputPath);
        }
    }
    
    @Then("^directory \"([^\"]*)\" should not exist$")
    public void directory_should_not_exist(String directory) {
        File dir = new File(parentProjectDir, directory);
        assertThat(dir, not(FileMatchers.exists()));
    }
    
    @Then("^output file \"([^\"]*)\" should contain \"([^\"]*)\"$")
    public void output_file_should_contain(String fileName, String expectedPieceOfContent) throws Throwable {
        String outputPath = FilePaths.join(websiteDestinationDirectory.getAbsolutePath(), this.websiteName, fileName);
        File outputFile = new File(outputPath);
        String fileContent = Files.toString(outputFile, Charsets.UTF_8);
        assertThat(fileContent, containsString(expectedPieceOfContent));
    }
}
