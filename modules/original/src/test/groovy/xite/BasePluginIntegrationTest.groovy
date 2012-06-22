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
    static protected Logger logger = LoggerFactory.getLogger(BasePluginIntegrationTest.class.getName());
    
    File targetDir;
    ConfigObject testConfiguration
    Paths testPaths

    protected void buildEnvironmentForsampleApp(String appName)
    {
        String xiteHome = System.getProperty('xite.itest.XITE_HOME')
        testPaths = new Paths(xiteHome)
    	String rootDir = System.getProperty("xite.itest.project.rootDir"); 
    	targetDir = new File(rootDir+"/target/itest"); 
        logger.debug("targetDir '{}' exists? {}", targetDir, targetDir.exists());
        File itestDefaultConfigurationFile = new File(rootDir+'/src/dist/conf/xite-default.groovy')
        logger.debug("itestDefaultConfigurationFile ${itestDefaultConfigurationFile}")
        ConfigSlurper slurper = new ConfigSlurper(null)
        ConfigObject itestDefaultConfiguration = slurper.parse(itestDefaultConfigurationFile.toURL())
        File sourceDir = new File("${rootDir}/samples/${appName}/src/xite")
        testPaths.sourceDirectory = testPaths.normalize(sourceDir.getAbsolutePath())
        File projectConfigurationFile = new File("${testPaths.sourceDirectory}/xite/site.groovy")
        logger.debug("projectConfigurationFile ${projectConfigurationFile} ${projectConfigurationFile.getAbsolutePath()}")
        ConfigObject projectConfig = new ConfigSlurper().parse(projectConfigurationFile.toURL())
        testConfiguration = (ConfigObject) itestDefaultConfiguration.merge(projectConfig);
        testPaths.destinationDirectory = testPaths.normalize(targetDir.getAbsolutePath())
    }
}
