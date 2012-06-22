package com.github.enr.xite;

/*
 * interface for Pick commands
 */
public interface Command {

	/*
	 * args is supposed to contain command arguments (with JCommander annotations)
	 */
	CommandResult execute(Object args);

	/*
	 * returns a parameters container object (ie an instance annotated with Jcommander @Parameters 
	 */
	//Object parameters();
}
