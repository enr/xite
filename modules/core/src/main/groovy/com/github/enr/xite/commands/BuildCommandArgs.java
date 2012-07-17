package com.github.enr.xite.commands;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandDescription = "Build the site")
public class BuildCommandArgs {
	@Parameter(names = { "-s", "--source" }, description = "Source directory")
	public String source;
	@Parameter(names = { "-d", "--destination" }, description = "Destination directory")
	public String destination;

}
