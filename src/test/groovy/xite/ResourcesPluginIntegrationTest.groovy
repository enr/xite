package xite;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;

import xite.Strings

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Integration test for resources plugin.
 *
 */
public class ResourcesPluginIntegrationTest extends BasePluginIntegrationTest
{
  
    /*
     * 
     */
    @Test
    public void testBasicProcess()
    {
    	File resource = new File(targetDir.getAbsolutePath()+'/icon_success.gif');
        assertTrue(resource.exists(), "${resource.getAbsolutePath()} not found");
    }
    
    /*
     * 
     */
    @Test
    public void testAdditionalResourcesInSubDirectory()
    {
    	File additional = new File(targetDir.getAbsolutePath()+'/add/test.html');
        assertTrue(additional.exists(), "${additional.getAbsolutePath()} not found");
    }
    
}
