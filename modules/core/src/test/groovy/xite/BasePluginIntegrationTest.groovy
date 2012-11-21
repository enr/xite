package xite;

import java.io.File

import javax.inject.Inject

import org.testng.annotations.Guice

import com.github.enr.clap.api.Configuration
import com.github.enr.clap.api.EnvironmentHolder
import com.github.enr.clap.api.Reporter
import com.github.enr.clap.util.ClasspathUtil
import com.github.enr.xite.util.FilePaths;

/**
 * Base class for plugins integration tests.
 *
 */
@Guice(modules = IntegrationTestModule.class)
public class BasePluginIntegrationTest
{

    @Inject Configuration testConfiguration
    @Inject EnvironmentHolder environment
    @Inject Reporter reporter

    File targetDir;
	String sourceDirectory
	String destinationDirectory

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
    	targetDir = new File("${fakeHome}/itest");
        File itestDefaultConfigurationFile = new File("${projectRoot}/src/test/resources/xite-default.groovy")
        assert itestDefaultConfigurationFile.exists()
        testConfiguration.addPath("${projectRoot}/src/test/resources/xite-default.groovy")
        File sourceDir = new File("${projectRoot}/src/test/sites/${appName}/src/xite")
        sourceDirectory = FilePaths.absoluteNormalized(sourceDir)
        File projectConfigurationFile = new File(FilePaths.join(sourceDir.getAbsolutePath(), 'xite', "site.groovy"))
        testConfiguration.addPath(projectConfigurationFile.getAbsolutePath())
        destinationDirectory = FilePaths.absoluteNormalized(targetDir)
    }
}
