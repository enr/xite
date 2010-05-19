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
 * Integration test for code plugin.
 *
 */
public class CodePluginIntegrationTest extends BasePluginIntegrationTest
{
       
    /*
     * 
     */
    @Test
    public void testBaseProcess()
    {    
        def headerHtmlContent = '''<html>
<head></head>
<body>

<pre><code lang="html">
&#60;html&#62;
&#60;head&#62;&#60;/head&#62;
&#60;body&#62;


</code></pre></body>
</html>
'''
    	File headerHtml = new File(targetDir.getAbsolutePath()+'/code/header.html.html');
        assertTrue(headerHtml.exists(), "${headerHtml.getAbsolutePath()} not found");
        assertEquals(headerHtml.text, headerHtmlContent);
    }
        
    /*
     * 
     */
    @Test
    public void testSubDirectory()
    {    
        def groovyFileContent = '''<html>
<head></head>
<body>

<pre><code lang="java">

println &#34;hi &#224;&#232;&#236;&#242;&#249;&#34;


</code></pre></body>
</html>
'''
    	File groovyFile = new File(targetDir.getAbsolutePath()+'/code/groovy/file.groovy.html');
        assertTrue(groovyFile.exists(), "${groovyFile.getAbsolutePath()} not found");
        assertEquals(groovyFile.text, groovyFileContent);
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
<p/><a href="/xite/code/groovy/file.groovy.html" class="dir-listing-f">groovy &gt; file.groovy.html</a>
<p/><a href="/xite/code/header.html.html" class="dir-listing-f">header.html.html</a>
</div>
</body>
</html>
'''
        indexContent = Strings.normalizeEol(indexContent)
    	File indexFile = new File(targetDir.getAbsolutePath()+'/code/index.html');
        assertTrue(indexFile.exists(), "${indexFile.getAbsolutePath()} not found");
        assertEquals(indexFile.text, indexContent);
    }    
    


}
