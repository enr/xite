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
        assertFalse(exc.exists());
    }
        
    /*
     * 
     */
    @Test
    public void testEscaping()
    {   
    	File escaped = new File(targetDir.getAbsolutePath()+'/special-chars.html');
        assertTrue(escaped.exists(), "${escaped.getAbsolutePath()} not found");
    	def actualEscaped = Strings.normalizeEol(escaped.getText("UTF-8"))
        def expectedEscaped = '''<html>
<head></head>
<body>

<h1>Xite</h1>

<p>Xite ìs à statiç sités generatòr.
It ùses &lt;Groovy> &amp; &lt;Gradle>.</p>
</body>
</html>
'''
        String es = new String(expectedEscaped.toString().getBytes(), 'UTF-8')
        es = Strings.normalizeEol(es)
        assertEquals actualEscaped, es
    }
    
    @Test
    public void testSyntax()
    {
    	File index = new File(targetDir, "syntax.html");
    	assertTrue(index.exists());
    	def actualIndex = Strings.normalizeEol(index.text)
        def expectedIndex = '''<html>
<head></head>
<body>

<p>Xite is a tool for static site generation from source files which can be in various formats.</p>

<p><a href="http://daringfireball.net/projects/markdown" title="Markdown">Markdown</a> syntax.</p>

<p>Another <a href="http://example.tld">http://example.tld</a> link</p>
</body>
</html>
'''
        expectedIndex = Strings.normalizeEol(expectedIndex)
        assertEquals actualIndex, expectedIndex
    }

}
