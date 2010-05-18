package xite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;


/**
 * Base class for plugins integration tests.
 *
 */
public class BasePluginIntegrationTest
{
    static protected Logger logger;

    @SuppressWarnings("static-access")
    protected Logger log()
    {
        if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass());
        return this.logger;
    }
    
    File targetDir;

    @BeforeClass
    public void init()
    {
    	String rootDir = System.getProperty("xite.itest.project.rootDir"); 
    	targetDir = new File(rootDir+"/target/itest"); 
        log().info("targetDir '{}' {}", targetDir, targetDir.exists());
    }

}
