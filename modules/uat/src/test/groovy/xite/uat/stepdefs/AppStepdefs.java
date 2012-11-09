package xite.uat.stepdefs;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.testng.Assert.assertTrue;

import java.io.File;

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
import cucumber.runtime.PendingException;

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
    
    @When("^I run xite with \"([^\"]*)\" args$")
    public void I_run_xite_with(String argsAsString) throws Exception {
        Module testModule = new XiteModule();
        String[] args = argsAsString.split("\\s");
        RunResult result = Clap.runReviewableApp(args, this.sutHome, testModule);
        this.sutExitValue = result.getExitValue();
        this.sutOutput = result.getOutput();
    }
    
    @When("^I build the actual Xite's website$")
    public void I_build_actual_xite_website() throws Exception {
        Module testModule = new XiteModule();
        //String websitePath = new StringBuilder(parentProjectDir.getAbsolutePath()).append(File.separatorChar).append("website").toString();
        //String argsAsString = "";
        xiteWebsiteSourceDirectory = new File(parentProjectDir, "website");
        String websitePath = FilePaths.absoluteNormalized(xiteWebsiteSourceDirectory);
        xiteWebsiteDestinationDirectory = new File("target/uat");
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

}
