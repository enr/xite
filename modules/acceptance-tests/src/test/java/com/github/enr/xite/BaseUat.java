package com.github.enr.xite;

import java.io.File;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import xite.ClasspathUtil;
import xite.XiteMain;

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

    protected int runApplicationWithArgs(String[] args) {
        XiteMain xite = new XiteMain();
        return xite.process(installedHome, args);
    }

}