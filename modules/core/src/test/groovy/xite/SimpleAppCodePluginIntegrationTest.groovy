package xite;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;

import java.io.File;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.enr.xite.plugins.CodePlugin;
import com.github.enr.xite.util.Strings;

/**
 * Integration test for code plugin applied to simple app.
 * 
 */
//@Test(suiteName = "Code plugin")
public class SimpleAppCodePluginIntegrationTest extends BasePluginIntegrationTest {
    CodePlugin plugin;

    @BeforeClass
    public void init()
    {
        buildEnvironmentForsampleApp('simple')
		def configurationPaths = testConfiguration.getPaths()
		configurationPaths.each { k, v ->
			reporter.warn " --- ${k} [ ${v} ]"
		}
        plugin = new CodePlugin(configuration: testConfiguration, sourcePath:sourceDirectory, destinationPath:destinationDirectory,
            reporter:reporter, environment:environment)
        plugin.apply()
    }

    @Test
    public void testBaseProcess()
    {    
        def headerHtmlContent = '''<html>
<head></head>
<body><p/><h3>/header.html</h3><p/><pre><code lang="html">
<html>
<head></head>
<body>
</code></pre></body>
</html>
'''
    	File headerHtml = new File(targetDir.getAbsolutePath()+'/code/header.html.html');
        assertTrue(headerHtml.exists(), "${headerHtml.getAbsolutePath()} not found");
        assertEquals(headerHtml.getText("UTF-8"), headerHtmlContent);
    }

    /*
     * 
     */
    @Test
    public void testSubDirectory()
    {
        File groovyFile = new File(targetDir.getAbsolutePath()+'/code/groovy/file.groovy.html');
        assertTrue(groovyFile.exists(), "${groovyFile.getAbsolutePath()} not found");
    	def actualContent = Strings.normalizeEol(groovyFile.getText("UTF-8"));
        
        def groovyFileContent = "<html>\n<head></head>\n<body><p/><h3>/groovy/file.groovy</h3><p/><pre><code lang=\"groovy\">\n\nprintln \"hi àèìòù\"\n\n\n</code></pre></body>\n</html>\n"
        String expectedContent = new String(groovyFileContent.toString().getBytes("UTF-8"), "UTF-8");
        expectedContent = Strings.normalizeEol(expectedContent);
        assertEquals(actualContent, expectedContent);
    }

    /*
     * 
     */
    @Test
    public void testIndexPage()
    {    
        def indexContent = '''<html>
<head></head>
<body>
<div id="dir-listing">

<p/><span class="dir-listing-d">groovy</span>
<p/><a href="/simple/code/groovy/file.groovy.html" class="dir-listing-f">groovy &gt; file.groovy.html</a>
<p/><a href="/simple/code/header.html.html" class="dir-listing-f">header.html.html</a>
</div>
</body>
</html>
'''
        indexContent = Strings.normalizeEol(indexContent)
        def destinationPath = targetDir.getAbsolutePath()+'/code/index.html'
    	File indexFile = new File(destinationPath);
        assertTrue(indexFile.exists(), "${indexFile.getAbsolutePath()} not found");
        println '___'
        println indexFile.text
        println '___'
        println indexContent
        println '___'
        assertEquals(indexFile.text, indexContent);
    }
}
