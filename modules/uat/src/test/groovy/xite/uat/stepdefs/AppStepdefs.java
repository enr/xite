package xite.uat.stepdefs;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.List;

import com.github.enr.clap.Clap;
import com.github.enr.clap.Clap.RunResult;
import com.github.enr.clap.util.ClasspathUtil;
import com.github.enr.xite.inject.XiteModule;
import com.github.enr.xite.util.FilePaths;
import com.google.inject.Module;

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
    
    @Given("^default properties:$")
    public void default_properties(List<DefaultProperties> items) throws Throwable {
        /*
        for (DefaultProperties item : items) {
            shoppingList.addItem(item.key, item.value);
        }
        */
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
        String websitePath = FilePaths.absoluteNormalized(new File(parentProjectDir, "website"));
        File destinationDirectory = new File("target/uat");
        String destinationPath = destinationDirectory.getAbsolutePath();
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
    
    // When converting tables to a List of objects it's usually better to
    // use classes that are only used in test (not in production). This
    // reduces coupling between scenarios and domain and gives you more control.
    public static class DefaultProperties {
        private String key;
        private String value;
    }
}
