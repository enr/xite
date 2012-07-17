package com.github.enr.xite.commands;

import javax.inject.Inject;

import com.github.enr.clap.api.AbstractCommand;
import com.github.enr.clap.api.CommandResult;
import com.github.enr.clap.api.Configuration;
import com.github.enr.clap.api.EnvironmentHolder;
import com.github.enr.clap.api.Reporter;

/*
 * Run the produced site.
 */
public class RunCommand extends AbstractCommand {
	
	private EnvironmentHolder environment;
	private Configuration configuration;
	private Reporter reporter;

	private RunCommandArgs args = new RunCommandArgs();
	
	@Inject
	public RunCommand(EnvironmentHolder environment, Configuration configuration, Reporter reporter) {
		this.environment = environment;
		this.configuration = configuration;
		this.reporter = reporter;
	}

	@Override
	public void init() {
		
	}

	@Override
	protected CommandResult internalExecute() {
		reporter.info("exec: destination dir = %s", args.port);
		reporter.info("exec: destination dir = %s", configuration.get("project.destination", args.destination));
		return new CommandResult();
	}

	@Override
	public String getId() {
		return "run";
	}

	@Override
	public Object getParametersContainer() {
		return args;
	}

}
