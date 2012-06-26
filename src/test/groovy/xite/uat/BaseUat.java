package xite.uat;
import java.io.File;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import xite.ClasspathUtil;
import xite.XiteMain;

public class BaseUat {

    /**
     * The home for the xite installation used for the tests.
     */
    File installedHome;

    @BeforeClass
    public void setUp() throws Exception {
        installedHome = new File("target/install/xite");
    }

    @AfterClass
    public void tearDown() {

    }

    protected int runApplicationWithArgs(String[] args) {
        XiteMain xite = new XiteMain();
        return xite.process(installedHome, args);
    }

}