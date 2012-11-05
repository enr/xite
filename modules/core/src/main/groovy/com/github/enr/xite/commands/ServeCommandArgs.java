package com.github.enr.xite.commands;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandDescription = "Serve the produced site")
public class ServeCommandArgs {
    @Parameter(names = { "-p", "--port" }, description = "Port")
    public int port = 8080;
    @Parameter(names = { "-d", "--destination", "-r", "--root" }, description = "Root directory")
    public String root;

}
