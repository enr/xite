package xite;

import java.io.File;

import org.apache.commons.io.FileUtils;
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
        File cc = ClasspathUtil.getClasspathForClass(BasePluginIntegrationTest.class);
        File modules = cc.getParentFile().getParentFile().getParentFile().getParentFile();
		String xiteRoot = modules.getParentFile().getAbsolutePath();
		String projectRoot = new StringBuilder(modules.getAbsolutePath()).append(File.separatorChar)
		.append("core").toString()
		String fakeHome = new StringBuilder(modules.getAbsolutePath()).append(File.separatorChar)
		.append("core").append(File.separatorChar)
		.append("target").toString()
		        //String xiteHome = "target" //System.getProperty('xite.itest.XITE_HOME')
        testPaths = new Paths(fakeHome)
    	//String rootDir = System.getProperty("xite.itest.project.rootDir"); 
    	targetDir = new File("${fakeHome}/itest"); 
        logger.debug("targetDir '{}' exists? {}", targetDir, targetDir.exists());
        File itestDefaultConfigurationFile = new File("${projectRoot}/src/dist/conf/xite-default.groovy")
        logger.debug("itestDefaultConfigurationFile ${itestDefaultConfigurationFile}")
        ConfigSlurper slurper = new ConfigSlurper(null)
        ConfigObject itestDefaultConfiguration = slurper.parse(itestDefaultConfigurationFile.toURI().toURL())
        File sourceDir = new File("${projectRoot}/src/test/sites/${appName}/src/xite")
        testPaths.sourceDirectory = testPaths.normalize(sourceDir.getAbsolutePath())
        File projectConfigurationFile = new File("${testPaths.sourceDirectory}/xite/site.groovy")
        logger.debug("projectConfigurationFile ${projectConfigurationFile} ${projectConfigurationFile.getAbsolutePath()}")
        ConfigObject projectConfig = new ConfigSlurper().parse(projectConfigurationFile.toURI().toURL())
        testConfiguration = (ConfigObject) itestDefaultConfiguration.merge(projectConfig);
        testPaths.destinationDirectory = testPaths.normalize(targetDir.getAbsolutePath())
    }
}
