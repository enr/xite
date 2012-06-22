package com.github.enr.xite;

import javax.inject.Inject;

import com.github.enr.xite.EnvironmentHolder;
import com.github.enr.xite.Reporter;


/*
 * lists available datasets.
 */
public class RunCommand implements Command {
	
	private EnvironmentHolder environment;
	private Reporter reporter;
	
	@Inject
	public RunCommand(EnvironmentHolder environment, Reporter reporter) {
		this.reporter = reporter;
		this.environment = environment;
		this.reporter.debug("Command %s", this.getClass().getName());
		this.reporter.debug("e %s", this.environment);
	}

	@Override
	public CommandResult execute(Object args) {
		reporter.debug("args = %s", args);
		return new CommandResult();
	}

	/*
	@Override
	public Object parameters() {
		return new ListCommandArgs();
	}
	*/
}
