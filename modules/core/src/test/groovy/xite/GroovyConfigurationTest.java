package xite;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.net.URL;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.github.enr.xite.ClasspathUtil;
import com.github.enr.xite.ConfigurationReader;
import com.github.enr.xite.GroovierConfigurationReader;

public class GroovyConfigurationTest
{
    
    ConfigurationReader configuration;
    File home;
    
    @BeforeMethod
    public void initConfiguration()
    {
        configuration = new GroovierConfigurationReader();
        File location = ClasspathUtil.getClasspathForClass(GroovyConfigurationTest.class);
        if (home == null) {
            home = location.getParentFile().getParentFile().getParentFile().getParentFile().getParentFile();
        }
    }
    
    @AfterMethod
    public void resetConfiguration()
    {
        configuration.reset();
    }

    @Test
    public void testConfigurationBuilding() throws Exception
    {
        URL firstConfig = testResource("/groovy-config-1.groovy");
        assertNotNull(firstConfig);
        configuration.addConfiguration(firstConfig);
        configuration.addBinding("binded", "xxx");
        String enviroment = "production";
        boolean buildSuccess = configuration.build(enviroment);
        assertTrue(buildSuccess);
        String e = configuration.get("e");
        assertEquals(e, "e-production");
        Boolean aOne = configuration.get("a.one");
        assertTrue(aOne);
    }

    @Test
    public void testBinding() throws Exception
    {
        URL firstConfig = testResource("/groovy-config-1.groovy");
        assertNotNull(firstConfig);
        configuration.addConfiguration(firstConfig);
        configuration.addBinding("binded", "B");
        boolean buildSuccess = configuration.build();
        assertTrue(buildSuccess);
        assertEquals(configuration.get("b"), "B1");
    }

    @Test
    public void testGroovyString() throws Exception
    {
        URL firstConfig = testResource("/groovy-config-1.groovy");
        assertNotNull(firstConfig);
        configuration.addConfiguration(firstConfig);
        configuration.addBinding("binded", "B");
        boolean buildSuccess = configuration.build();
        assertTrue(buildSuccess);
        assertEquals(configuration.get("groovier"), "Hello Groovy!");
        //assertEquals(configuration.get("groovier", String.class), "Hello Groovy!");
        //assertEquals(configuration.get("groovier", GStringImpl.class), null);
    }

    @Test
    public void testConfigurationMerging() throws Exception
    {
        String enviroment = "production";
        URL firstConfig = testResource("first-config.groovy");
        assertNotNull(firstConfig);
        URL secondConfig = testResource("second-config.groovy");
        assertNotNull(secondConfig);
        configuration.addConfiguration(firstConfig);
        configuration.addConfiguration(secondConfig);
        configuration.addBinding("binded", "B");
        boolean buildSuccess = configuration.build(enviroment);
        assertTrue(buildSuccess);
        assertEquals(configuration.get("server.URL"), "http://prod/url");
        assertEquals(configuration.get("app.version"), "2.0");
    }
    
    @Test
    public void testActualConfiguration() throws Exception
    {
        URL firstConfig = testResource("xite-default.groovy");
        assertNotNull(firstConfig);
        configuration.addConfiguration(firstConfig);
        boolean buildSuccess = configuration.build();
        assertTrue(buildSuccess);
        assertEquals(configuration.get("app.version"), "0.1-SNAPSHOT");
        assertEquals(configuration.get("deploy.ftp.enabled"), true);
        //List<String> plugins = configuration.get("plugins.enabled");
        
        //assertEquals(configuration.get("groovier", String.class), "Hello Groovy!");
        //assertEquals(configuration.get("groovier", GStringImpl.class), null);
    }
    
    private URL testResource(String name) throws Exception {
    	File f = new File(home + "/modules/core/src/test/resources/"+name);
    	System.out.printf("f  %s%n", f.getAbsolutePath());
    	System.out.printf("f  %s%n", f.exists());
    	return f.toURI().toURL();
    }
}
