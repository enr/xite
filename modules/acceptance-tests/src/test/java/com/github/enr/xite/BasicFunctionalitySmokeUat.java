package com.github.enr.xite;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * A very simple smoke test which fails if any exception is throwed.
 */
@Test(suiteName="uat")
public class BasicFunctionalitySmokeUat extends BaseUat {

    @Test(dataProvider = "basic-functions", description = "base functionality is working")
    public void basicFunctionsAreWorking(String[] args) {
        runApplicationWithArgs(args);
    }

    /*
     * Creates arguments for basic calling
     */
    @DataProvider(name = "basic-functions")
    public Object[][] basicFunctions() {
        return new Object[][] { new Object[] { new String[] {} },
                				new Object[] { new String[] { "--help" } } };
    }

}
