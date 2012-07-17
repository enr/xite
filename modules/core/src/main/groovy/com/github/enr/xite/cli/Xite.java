package com.github.enr.xite.cli;

import com.github.enr.clap.api.ClapApp;
import com.github.enr.clap.inject.Bindings;
import com.github.enr.clap.inject.ClapModule;
import com.github.enr.xite.inject.XiteModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.util.Modules;

public class Xite {

	public static void main(String[] args) {
        Injector injector = Guice.createInjector(Modules.override(new ClapModule()).with(new XiteModule()));
        ClapApp app = injector.getInstance(ClapApp.class);
        app.setAvailableCommands(Bindings.getAllCommands(injector));
        app.run(args);
	}

}
