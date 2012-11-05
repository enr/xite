package com.github.enr.xite.commands;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandDescription = "Deploy final artifact to remote server using FTP")
public class DeployCommandArgs {

    @Parameter(names = { "-d", "--destination" }, description = "Destination directory")
    public String destination;
}
