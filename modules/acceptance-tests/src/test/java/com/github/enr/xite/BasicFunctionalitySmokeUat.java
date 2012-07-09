package com.github.enr.xite;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


/**
* A very simple smoke test which fails if any exception is throwed.
*/
@Test(suiteName = "User acceptance tests")
public class BasicFunctionalitySmokeUat extends BaseUat {

    @Test(dataProvider = "basic-functions-success", description = "base functionality is working")
    public void basicFunctionsAreWorking(String[] args) {
        int result = runApplicationWithArgs(args);
        assertEquals(result, 0);
    }

    @Test(dataProvider = "basic-functions-error", description = "base functionality is working")
    public void basicFunctionsInError(String[] args) {
        int result = runApplicationWithArgs(args);
        assertEquals(result, 1);
    }
    
    /*
     * Creates arguments for basic calling
     */
    @DataProvider(name = "basic-functions-success")
    public Object[][] basicFunctionsSuccess() {
        return new Object[][] { 
        	new Object[] { new String[] {"--help"} }
        	//, new Object[] { new String[] { "reports" } },
            //new Object[] { new String[] { "empty-op" } } 
        };
    }
    
    @DataProvider(name = "basic-functions-error")
    public Object[][] basicFunctionsError() {
        return new Object[][] { 
        	new Object[] { new String[] { "no-such-command" } } 
        };
    }
}