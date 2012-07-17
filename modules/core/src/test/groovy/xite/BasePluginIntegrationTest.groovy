package xite;

import java.io.File

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.github.enr.clap.api.Configuration
import com.github.enr.clap.api.EnvironmentHolder
import com.github.enr.clap.api.Reporter
import com.github.enr.clap.inject.ClapModule
import com.github.enr.xite.inject.XiteModule
import com.google.inject.Guice
import com.google.inject.Injector
import com.google.inject.util.Modules


/**
 * Base class for plugins integration tests.
 *
 */
public class BasePluginIntegrationTest
{
    static protected Logger logger = LoggerFactory.getLogger(BasePluginIntegrationTest.class.getName());
    
    File targetDir;
    Configuration testConfiguration
    EnvironmentHolder environment
    Reporter reporter
    Paths testPaths

    protected void buildEnvironmentForsampleApp(String appName)
    {
        Injector injector = Guice.createInjector(Modules.override(new ClapModule()).with(new XiteModule()));
        testConfiguration = injector.getInstance(Configuration.class);
        environment = injector.getInstance(EnvironmentHolder.class);
        reporter = injector.getInstance(Reporter.class);
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
        testConfiguration.add(itestDefaultConfigurationFile.toURI().toURL())
        File sourceDir = new File("${projectRoot}/src/test/sites/${appName}/src/xite")
        testPaths.sourceDirectory = testPaths.normalize(sourceDir.getAbsolutePath())
        File projectConfigurationFile = new File("${testPaths.sourceDirectory}/xite/site.groovy")
        logger.debug("projectConfigurationFile ${projectConfigurationFile} ${projectConfigurationFile.getAbsolutePath()}")
        testConfiguration.add(projectConfigurationFile.toURI().toURL())
        //testConfiguration = (ConfigObject) itestDefaultConfiguration.merge(projectConfig);
        testPaths.destinationDirectory = testPaths.normalize(targetDir.getAbsolutePath())
    }
}
