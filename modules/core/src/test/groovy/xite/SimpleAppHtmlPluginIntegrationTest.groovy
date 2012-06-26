package xite;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;

import xite.Strings
import xite.Paths
import xite.plugins.HtmlPlugin;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Integration test for html plugin applied to simple app.
 *
 */
public class SimpleAppHtmlPluginIntegrationTest extends BasePluginIntegrationTest
{
    HtmlPlugin plugin;

    @BeforeClass
    public void init()
    {
        buildEnvironmentForsampleApp('simple')
        plugin = new HtmlPlugin(configuration: testConfiguration, paths: testPaths)
        plugin.apply()
    }

    /*
     * 
     */
    @Test
    public void testBasicProcess()
    {
        def firstContent = '''<html>
<head></head>
<body>

first.html
</body>
</html>
'''
        firstContent = Strings.normalizeEol(firstContent);
    	File firstHtml = new File(targetDir.getAbsolutePath()+'/first.html');
        assertTrue(firstHtml.exists(), "${firstHtml.getAbsolutePath()} not found");

        assertEquals(firstHtml.text, firstContent);

        def secondContent = '''<html>
<head></head>
<body>

second.html
</body>
</html>
'''
    	File secondHtml = new File(targetDir.getAbsolutePath()+'/second.html');
        assertTrue(secondHtml.exists(), "${secondHtml.getAbsolutePath()} not found");
        assertEquals(secondHtml.text, secondContent);
    }
    
    /*
     * 
     */
    @Test
    public void testSubDirectoryHtmlPlugin()
    {    
        def fooBarContent = '''<html>
<head></head>
<body>

foo/bar.html
</body>
</html>
'''
    	File fooBarHtml = new File(targetDir.getAbsolutePath()+'/foo/bar.html');
        assertTrue(fooBarHtml.exists(), "${fooBarHtml.getAbsolutePath()} not found");
        assertEquals(fooBarHtml.text, fooBarContent);
    }
        

}
