package com.github.enr.xite.commands;

import javax.inject.Inject;

import com.github.enr.clap.api.AbstractCommand;
import com.github.enr.clap.api.CommandResult;
import com.github.enr.clap.api.Configuration;
import com.github.enr.clap.api.EnvironmentHolder;
import com.github.enr.clap.api.Reporter;

/*
 * clean output directory
 */
public class CleanCommand extends AbstractCommand {
	
	private EnvironmentHolder environment;
	private Configuration configuration;
	private Reporter reporter;
	
	private CleanCommandArgs args = new CleanCommandArgs();
	
	//private Paths paths;
	
	@Inject
	public CleanCommand(EnvironmentHolder environment, Configuration configuration, Reporter reporter) {
		this.environment = environment;
		this.configuration = configuration;
		this.reporter = reporter;
	}

	@Override
	public void init() {
		String homeDirectory = environment.applicationHome().getAbsolutePath();
		reporter.info("init: home dir = %s", homeDirectory);
		//paths = new Paths(homeDirectory);
	}

	@Override
	protected CommandResult internalExecute() {
		reporter.info("exec: destination dir = %s", configuration.get("project.destination", args.destination));
		return new CommandResult();
	}

	@Override
	public String getId() {
		return "clean";
	}

	@Override
	public Object getParametersContainer() {
		return args;
	}

}
