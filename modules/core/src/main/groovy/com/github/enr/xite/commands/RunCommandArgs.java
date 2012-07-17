package com.github.enr.xite.commands;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandDescription = "Run the produced site")
public class RunCommandArgs {
	@Parameter(names = { "-p", "--port" }, description = "Port")
	public int port = 8080;
	@Parameter(names = { "-d", "--destination" }, description = "Destination directory")
	public String destination;

}
