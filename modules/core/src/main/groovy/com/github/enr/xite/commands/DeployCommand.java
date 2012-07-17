package com.github.enr.xite.commands;

import javax.inject.Inject;

import com.github.enr.clap.api.AbstractCommand;
import com.github.enr.clap.api.CommandResult;
import com.github.enr.clap.api.Configuration;
import com.github.enr.clap.api.EnvironmentHolder;
import com.github.enr.clap.api.Reporter;

/*
 * Deploy final artifact to remote server using FTP.
 */
public class DeployCommand extends AbstractCommand {
	
	private EnvironmentHolder environment;
	private Configuration configuration;
	private Reporter reporter;

	private DeployCommandArgs args = new DeployCommandArgs();
	
	@Inject
	public DeployCommand(EnvironmentHolder environment, Configuration configuration, Reporter reporter) {
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
		this.reporter.debug("ftp = %s", configuration.get("deploy.ftp"));
		this.reporter.debug("plugins = %s", configuration.get("plugins.enabled"));
		reporter.info("exec: destination dir = %s", configuration.get("project.destination", args.destination));
		return new CommandResult();
	}

	@Override
	public String getId() {
		return "deploy";
	}

	@Override
	public Object getParametersContainer() {
		return args;
	}

}
