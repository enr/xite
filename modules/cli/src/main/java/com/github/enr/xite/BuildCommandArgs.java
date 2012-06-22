package com.github.enr.xite;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

/*
 * parameters for 'build' command
 */
@Parameters(commandDescription = "Build website")
public class BuildCommandArgs {

    @Parameter(names = {"-s", "--source"}, description = "Source directory")
    private String source = "./src/xite";
    
    @Parameter(names = {"-d", "--destination"}, description = "Destination directory")
    private String destination = "./target/xite";

	public String getSource() {
		return source;
	}

	public String getDestination() {
		return destination;
	}
}
