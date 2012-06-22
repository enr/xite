package com.github.enr.xite;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

/*
 * parameters for main command.
 */
@Parameters
public class MainCommandArgs {

	@Parameter(names = { "-h", "--help" }, description = "Print this help")
	private boolean help = false;
	
	@Parameter(names = { "-d", "--debug" }, description = "Set output level to debug")
	private boolean debug = false;
	
	@Parameter(names = { "-i", "--info" }, description = "Set output level to info")
	private boolean info = false;

	public boolean isHelp() {
		return help;
	}

	public boolean isInfo() {
		return info;
	}

	public boolean isDebug() {
		return debug;
	}

}
