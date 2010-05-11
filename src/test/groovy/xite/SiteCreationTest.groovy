package xite;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import xite.Strings

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Integration test for site creation.
 *
 */
public class SiteCreationTest
{
    static private Logger logger;

    @SuppressWarnings("static-access")
    protected Logger log()
    {
        if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass());
        return this.logger;
    }
    
    File targetDir;

    @BeforeClass
    public void init()
    {
    	String rootDir = System.getProperty("xite.itest.project.rootDir"); 
    	targetDir = new File(rootDir+"/target/itest"); 
        log().info("targetDir '{}' {}", targetDir, targetDir.exists());
        
        // TODO creare file in dir resources e code con estensioni da escludere x
        // testExcludedExtensions
    }

    @Test
    public void testExcludedExtensions()
    {
        // assert file non esistono
    }
    
    @Test
    public void testMarkdownIndexPage()
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
    public void testResources()
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
    
    /*
     * 
     */
    @Test
    public void testHtmlPlugin()
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
        
    /*
     * 
     */
    @Test
    public void testCodePlugin()
    {    
        def headerHtmlContent = '''<html>
<head></head>
<body>

<pre><code lang="html">
&lt;html&gt;
&lt;head&gt;&lt;/head&gt;
&lt;body&gt;
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
    public void testCodePluginSubDirectory()
    {    
        def groovyFileContent = '''<html>
<head></head>
<body>

<pre><code lang="java">
println &quot;hi&quot;
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
    public void testCodeIndexpage()
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
