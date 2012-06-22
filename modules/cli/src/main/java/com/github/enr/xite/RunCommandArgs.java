package com.github.enr.xite;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandDescription = "Run website")
public class RunCommandArgs {

    @Parameter(names = {"-s", "--source"}, description = "Source directory")
    private String source = "./src/xite";
    
    @Parameter(names = {"-d", "--destination"}, description = "Destination directory")
    private String destination = "./target/xite";

    @Parameter(names = {"-p", "--port"}, description = "Port")
    private Integer port = 8080;

	public String getSource() {
		return source;
	}

	public String getDestination() {
		return destination;
	}

	public Integer getPort() {
		return port;
	}
}
