package com.github.enr.xite;

import javax.inject.Inject;

import com.github.enr.xite.Reporter;


/*
 * default command, executed if no command id is passed to Pick
 */
public class MainCommand implements Command {

	private final Reporter reporter;
	
	@Inject
	public MainCommand(Reporter reporter) {
		this.reporter = reporter;
		this.reporter.debug("command %s", this.getClass().getName());
	}
	
	@Override
	public CommandResult execute(Object args) {
		reporter.debug("main args = %s", args);
		return null;
	}

	/*
	@Override
	public Object parameters() {
		return new MainCommandArgs();
	}
	*/
}
