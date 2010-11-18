package xite;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;

import xite.Strings
import xite.Paths
import xite.plugin.html.HtmlPlugin

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Integration test for site creation.
 *
 */
public class HtmlPluginIntegrationTest //extends BasePluginIntegrationTest
{
    Logger logger = LoggerFactory.getLogger(this.getClass());

    File targetDir;

    HtmlPlugin plugin;

    @BeforeClass
    public void init()
    {
        String xiteHome = System.getProperty('xite.itest.XITE_HOME')
        Paths paths = new Paths(xiteHome)
    	String rootDir = System.getProperty("xite.itest.project.rootDir"); 
    	targetDir = new File(rootDir+"/target/itest"); 
        logger.debug("targetDir '{}' exists? {}", targetDir, targetDir.exists());
        File itestDefaultConfigurationFile = new File(paths.confDirectory+'/xite-default.groovy')
        logger.debug("itestDefaultConfigurationFile ${itestDefaultConfigurationFile}")
        ConfigSlurper slurper = new ConfigSlurper(null)
        ConfigObject itestDefaultConfiguration = slurper.parse(itestDefaultConfigurationFile.toURL())
        File sourceDir = new File(rootDir+"/test-app/src/xite")
        paths.sourceDirectory = paths.normalize(sourceDir.getAbsolutePath())
        File projectConfigurationFile = new File("${paths.sourceDirectory}/xite/site.groovy")
        logger.debug("projectConfigurationFile ${projectConfigurationFile} ${projectConfigurationFile.getAbsolutePath()}")
        ConfigObject projectConfig = new ConfigSlurper().parse(projectConfigurationFile.toURL())
        ConfigObject itestFinalConfiguration = (ConfigObject) itestDefaultConfiguration.merge(projectConfig);
        paths.destinationDirectory = paths.normalize(targetDir.getAbsolutePath())
        plugin = new HtmlPlugin(configuration: itestFinalConfiguration, paths: paths)
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
