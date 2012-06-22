package com.github.enr.xite;


import com.google.inject.Guice;
import com.google.inject.Injector;

/*
 * main class for Pick application.
 * 
 */
public class Main {
    public static void main(String[] args) throws Exception {
    	/*
    	BootstrapModule bootstrap = new BootstrapModule();
    	//bootstrap.setReporterLevel();
        Injector injector = Guice.createInjector(bootstrap);
        App app = injector.getInstance(App.class);
        Reporter reporter = injector.getInstance(Reporter.class);
        reporter.setLevel(Level.DEBUG);
        app.run(args);
        */
    	Injector injector = Guice.createInjector(new MainModule());
        App app = injector.getInstance(App.class);
        app.run(args);
    }
}
