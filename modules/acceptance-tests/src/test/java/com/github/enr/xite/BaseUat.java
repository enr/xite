package com.github.enr.xite;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;


import com.github.enr.xite.App;
import com.google.inject.Guice;
import com.google.inject.Injector;

/*
 * base class for user acceptance tests.
 * 
 */
public class BaseUat {

    @BeforeClass
    public void setUp() throws Exception {
    	/*
        File cc = ClasspathUtil.getClasspathForClass(BasicFunctionalitySmokeUat.class);
        File modules = cc.getParentFile().getParentFile().getParentFile().getParentFile();
        String installPath = Joiner.on(File.separatorChar).join(modules.getAbsolutePath(), "cli", "target", "install",
                "please");
        installedHome = new File(installPath);

        testDataPath = Joiner.on(File.separatorChar).join(modules.getAbsolutePath(), "acceptance-tests", "src", "test",
                "data");

        */
    }

    @AfterClass
    public void tearDown() {
        
    }

    /*
     * mimic Main class.
     */
    protected void runApplicationWithArgs(String[] args) {
        Injector injector = Guice.createInjector(new AcceptanceTestsModule());
        App app = injector.getInstance(App.class);
        app.run(args);
    }

}
