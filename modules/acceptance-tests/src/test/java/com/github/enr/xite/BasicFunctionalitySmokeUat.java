package com.github.enr.xite;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.beust.jcommander.MissingCommandException;


/**
* A very simple smoke test which fails if any exception is throwed.
*/
@Test(suiteName = "User acceptance smoke tests")
public class BasicFunctionalitySmokeUat extends BaseUat {

    @Test(dataProvider = "basic-functions-success", description = "base functionality is working")
    public void basicFunctionsAreWorking(String[] args) {
        runApplicationWithArgs(args);
        //assertEquals(result, 0);
    }

    @Test(dataProvider = "missing-commands", description = "if a missing command is called a MissingCommandException is thrown", expectedExceptions = {MissingCommandException.class})
    public void missingCommandsExceptionThrown(String[] args) {
        runApplicationWithArgs(args);
        //assertEquals(result, 1);
    }
    
    @Test(dataProvider = "error-args", description = "if user run xite with some error args, xite doesn't break")
    public void missingSourceDirectoryIsManaged(String[] args) {
        runApplicationWithArgs(args);
    }
    
    /*
     * Creates arguments for basic calling
     */
    @DataProvider(name = "basic-functions-success")
    public Object[][] basicFunctionsSuccess() {
        return new Object[][] { 
            new Object[] { new String[] { }}, 
        	new Object[] { new String[] { "--help" }}, 
        	new Object[] { new String[] { "--version" } } 
        };
    }
    
    @DataProvider(name = "missing-commands")
    public Object[][] missingCommands() {
        return new Object[][] { 
        	new Object[] { new String[] { "no-such-command" } } 
        };
    }
    
    @DataProvider(name = "error-args")
    public Object[][] errorArgs() {
        return new Object[][] { 
        	new Object[] { new String[] { "build", "-s", "/no/such/directory" } }, 
        	new Object[] { new String[] { "serve", "--root", "/no/such/directory" } } 
        };
    }
}