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
 * Integration test for markdown plugin.
 *
 */
public class MarkdownPluginIntegrationTest extends BasePluginIntegrationTest
{
    
    @Test
    public void testIndexPage()
    {
    	File index = new File(targetDir, "index.html");
    	assertTrue(index.exists());
    	def actualIndex = Strings.normalizeEol(index.text)
        def expectedIndex = '''<html>
<head></head>
<body>

<h1>Xite</h1>

<p>Xite is a static sites generator.</p>
</body>
</html>
'''
        expectedIndex = Strings.normalizeEol(expectedIndex)
        assertEquals actualIndex, expectedIndex
    }
        
    /*
     * 
     */
    @Test
    public void testExcludedExtension()
    {   
    	File exc = new File(targetDir.getAbsolutePath()+'/excluded.html');
        assertFalse(exc.exists(), "${exc.getAbsolutePath()} not found");
    }

}
