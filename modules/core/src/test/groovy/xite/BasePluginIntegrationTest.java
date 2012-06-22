package xite;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import junit.framework.Assert;

import org.testng.annotations.Test;


import com.github.enr.xite.Configuration;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class BasePluginIntegrationTest
{
    
    File targetDir;
    Configuration configuration;
    //Paths testPaths
    
    @Test
    public void test() throws MalformedURLException {
    	String appName = "$appname";
    	Injector injector = Guice.createInjector(new IntegrationTestsModule());
        configuration = injector.getInstance(Configuration.class);

    	File appHome = configuration.applicationHome();
    	String xiteHome = appHome.getAbsolutePath();
    	System.out.printf("home        %s%n", xiteHome);
    	String rootDir = configuration.applicationHome().getAbsolutePath();
    	System.out.printf("rootDir     %s%n", rootDir);
    	targetDir = new File(rootDir + "/modules/core/target/itest");
    	System.out.printf("targetDir   %s%n", targetDir);
        File itestDefaultConfigurationFile = new File(rootDir+"/modules/cli/src/dist/conf/xite-default.groovy");
    	System.out.printf("itest file  %s%n", itestDefaultConfigurationFile);
        File sourceDir = new File(rootDir+"/samples/"+appName+"/src/xite");
    	System.out.printf("sourceDir   %s%n", sourceDir);
    	
    	
		
		URL url = BasePluginIntegrationTest.class.getResource("first-config.groovy");
    	System.out.printf("1_  %s%n", url);
		url = BasePluginIntegrationTest.class.getResource("/first-config.groovy");
    	System.out.printf("2_  %s%n", url);
		url = BasePluginIntegrationTest.class.getResource("/p/xite-default.groovy");
    	System.out.printf("3_  %s%n", url);
		url = BasePluginIntegrationTest.class.getResource("p/xite-default.groovy");
    	System.out.printf("4_  %s%n", url);
		url = BasePluginIntegrationTest.class.getResource("/xite-default.groovy");
    	System.out.printf("5_  %s%n", url);
		url = BasePluginIntegrationTest.class.getResource("xite-default.groovy");
    	System.out.printf("6_  %s%n", url);
    	
    	File f = new File(xiteHome + "modules/core/src/test/resources/p/xite-default.groovy");
    	System.out.printf("f  %s%n", f.getAbsolutePath());
    	System.out.printf("f  %s%n", f.exists());
    	url = f.toURI().toURL();
    	System.out.printf("7_  %s%n", url);
    	
    	
    	
    	Assert.assertEquals(1, 1);
    }

    protected void buildEnvironmentForSampleApp(String appName)
    {

    	/*
        String xiteHome = System.getProperty('xite.itest.XITE_HOME')
        testPaths = new Paths(xiteHome)
        String rootDir = System.getProperty("xite.itest.project.rootDir");
        targetDir = new File(rootDir+"/target/itest");
        logger.debug("targetDir '{}' exists? {}", targetDir, targetDir.exists());
        File itestDefaultConfigurationFile = new File(rootDir+'/src/dist/conf/xite-default.groovy')
        logger.debug("itestDefaultConfigurationFile ${itestDefaultConfigurationFile}")
        ConfigSlurper slurper = new ConfigSlurper(null)
        ConfigObject itestDefaultConfiguration = slurper.parse(itestDefaultConfigurationFile.toURL())
        File sourceDir = new File("${rootDir}/samples/${appName}/src/xite")
        testPaths.sourceDirectory = testPaths.normalize(sourceDir.getAbsolutePath())
        File projectConfigurationFile = new File("${testPaths.sourceDirectory}/xite/site.groovy")
        logger.debug("projectConfigurationFile ${projectConfigurationFile} ${projectConfigurationFile.getAbsolutePath()}")
        ConfigObject projectConfig = new ConfigSlurper().parse(projectConfigurationFile.toURL())
        testConfiguration = (ConfigObject) itestDefaultConfiguration.merge(projectConfig);
        testPaths.destinationDirectory = testPaths.normalize(targetDir.getAbsolutePath())
        */
    }
}