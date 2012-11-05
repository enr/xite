package com.github.enr.xite.commands;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandDescription = "Clean the Xite destination directory")
public class CleanCommandArgs {
    @Parameter(names = { "-d", "--destination" }, description = "Destination directory")
    public String destination;
}
