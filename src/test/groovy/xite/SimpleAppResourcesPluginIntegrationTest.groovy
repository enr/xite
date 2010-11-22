package xite;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;

import xite.Strings
import xite.Paths
import xite.plugin.resources.ResourcesPlugin

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

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
        plugin = new ResourcesPlugin(configuration: testConfiguration, paths: testPaths)
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
