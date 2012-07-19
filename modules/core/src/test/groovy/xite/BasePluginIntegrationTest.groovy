package xite;

import java.io.File

import javax.inject.Inject

import org.testng.annotations.Guice

import com.github.enr.clap.api.Configuration
import com.github.enr.clap.api.EnvironmentHolder
import com.github.enr.clap.api.Reporter
import com.github.enr.clap.util.ClasspathUtil

/**
 * Base class for plugins integration tests.
 *
 */
@Guice(modules = IntegrationTestModule.class)
public class BasePluginIntegrationTest
{
    File targetDir;
    @Inject Configuration testConfiguration
    @Inject EnvironmentHolder environment
    @Inject Reporter reporter
    //Paths testPaths
	String sourceDirectory
	String destinationDirectory

    protected void buildEnvironmentForsampleApp(String appName)
    {
		/*
        Injector injector = Guice.createInjector(Modules.override(new ClapModule()).with(new XiteModule()));
        testConfiguration = injector.getInstance(Configuration.class);
        environment = injector.getInstance(EnvironmentHolder.class);
        reporter = injector.getInstance(Reporter.class);
		*/
        File cc = ClasspathUtil.getClasspathForClass(BasePluginIntegrationTest.class);
        File modules = cc.getParentFile().getParentFile().getParentFile().getParentFile();
		String xiteRoot = modules.getParentFile().getAbsolutePath();
		String projectRoot = new StringBuilder(modules.getAbsolutePath()).append(File.separatorChar)
		.append("core").toString()
		String fakeHome = new StringBuilder(modules.getAbsolutePath()).append(File.separatorChar)
		.append("core").append(File.separatorChar)
		.append("target").toString()
		        //String xiteHome = "target" //System.getProperty('xite.itest.XITE_HOME')
        //testPaths = new Paths(fakeHome)
    	//String rootDir = System.getProperty("xite.itest.project.rootDir");
    	targetDir = new File("${fakeHome}/itest"); 
        //reporter.out("targetDir '%s' exists? %s", targetDir, targetDir.exists());
        File itestDefaultConfigurationFile = new File("${projectRoot}/src/dist/conf/xite-default.groovy")
        //reporter.out("itestDefaultConfigurationFile ${itestDefaultConfigurationFile}")
        testConfiguration.addPath("${projectRoot}/src/dist/conf/xite-default.groovy")
        File sourceDir = new File("${projectRoot}/src/test/sites/${appName}/src/xite")
        sourceDirectory = FilePaths.absoluteNormalized(sourceDir)
        File projectConfigurationFile = new File(FilePaths.join(sourceDir.getAbsolutePath(), 'xite', "site.groovy"))
        //reporter.out("projectConfigurationFile ${projectConfigurationFile.getAbsolutePath()} exists? ${projectConfigurationFile.exists()}")
        testConfiguration.addPath(projectConfigurationFile.getAbsolutePath())
        //testConfiguration = (ConfigObject) itestDefaultConfiguration.merge(projectConfig);
        destinationDirectory = FilePaths.absoluteNormalized(targetDir)
    }
}
