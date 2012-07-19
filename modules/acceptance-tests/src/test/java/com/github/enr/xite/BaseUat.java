package com.github.enr.xite;

import java.io.File;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.github.enr.clap.api.ClapApp;
import com.github.enr.clap.api.Configuration;
import com.github.enr.clap.inject.Bindings;
import com.github.enr.clap.inject.ClapModule;
import com.github.enr.clap.util.ClasspathUtil;
import com.github.enr.xite.util.FilePaths;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.util.Modules;

public class BaseUat {

    /**
     * The home for the xite installation used for the tests.
     */
    File installedHome;
    
    File xiteRoot;

    @BeforeClass
    public void setUp() throws Exception {
    	
        File cc = ClasspathUtil.getClasspathForClass(BaseUat.class);
        File modules = cc.getParentFile().getParentFile().getParentFile().getParentFile();
        String installPath = new StringBuilder(modules.getAbsolutePath()).append(File.separatorChar)
        			.append("core").append(File.separatorChar)
        			.append("target").append(File.separatorChar)
        			.append("install").append(File.separatorChar)
        			.append("xite").toString();
        installedHome = new File(installPath);
        
        xiteRoot = modules.getParentFile();

    }

    @AfterClass
    public void tearDown() {

    }

    protected void runApplicationWithArgs(String[] args) {
        Injector injector = Guice.createInjector(Modules.override(new ClapModule()).with(new AcceptanceTestsModule()));
        Configuration configuration = injector.getInstance(Configuration.class);
        String absoluteNormalized = FilePaths.absoluteNormalized(installedHome);
        System.out.println (absoluteNormalized);
		configuration.addPath(absoluteNormalized + "/conf/xite.groovy");
        ClapApp app = injector.getInstance(ClapApp.class);
        app.setAvailableCommands(Bindings.getAllCommands(injector));
        app.run(args);
    }

}