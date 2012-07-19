package xite;


import static org.testng.Assert.assertEquals
import static org.testng.Assert.assertFalse
import static org.testng.Assert.assertTrue

import java.io.File

import org.testng.annotations.BeforeClass
import org.testng.annotations.Test

import com.github.enr.xite.plugins.ResourcesPlugin

/**
 * Integration test for resources plugin applied to simple app.
 *
 */
public class SimpleAppResourcesPluginIntegrationTest extends BasePluginIntegrationTest
{
    ResourcesPlugin plugin;

    @BeforeClass
    public void init()
    {
        buildEnvironmentForsampleApp('simple')
        plugin = new ResourcesPlugin(configuration: testConfiguration, sourcePath:sourceDirectory, destinationPath:destinationDirectory,
            reporter:reporter, environment:environment)
        plugin.apply()
    }
    
    @Test
    public void testBasicProcess()
    {
    	File resource = new File(targetDir.getAbsolutePath()+'/icon_success.gif');
        assertTrue(resource.exists(), "${resource.getAbsolutePath()} not found");
    }

    @Test
    public void testAdditionalResourcesInSubDirectory()
    {
		def paths = testConfiguration.getPaths()
    	File additional = new File(targetDir.getAbsolutePath()+'/add/test.html');
        assertTrue(additional.exists(), "${additional.getAbsolutePath()} not found");
    }

    @Test
    public void testSingleFile()
    {
    	File single = new File(targetDir.getAbsolutePath()+'/asinglefile.html');
        assertTrue(single.exists(), "${single.getAbsolutePath()} not found");
        assertEquals(single.text, '<p>hi</p>\n');
    }
}
