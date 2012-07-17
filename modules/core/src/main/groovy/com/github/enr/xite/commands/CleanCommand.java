package com.github.enr.xite.commands;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;

import com.github.enr.clap.api.AbstractCommand;
import com.github.enr.clap.api.CommandResult;
import com.github.enr.clap.api.Configuration;
import com.github.enr.clap.api.EnvironmentHolder;
import com.github.enr.clap.api.Reporter;
import com.google.common.base.Throwables;

/*
 * clean output directory
 */
public class CleanCommand extends AbstractCommand {
	
	private EnvironmentHolder environment;
	private Configuration configuration;
	private Reporter reporter;
	
	private CleanCommandArgs args = new CleanCommandArgs();
	
	//private Paths paths;
	
	@Inject
	public CleanCommand(EnvironmentHolder environment, Configuration configuration, Reporter reporter) {
		this.environment = environment;
		this.configuration = configuration;
		this.reporter = reporter;
	}

	@Override
	public void init() {
		String homeDirectory = environment.applicationHome().getAbsolutePath();
		reporter.info("init: home dir = %s", homeDirectory);
		//paths = new Paths(homeDirectory);
	}

	@Override
	protected CommandResult internalExecute() {
        String destinationPath = resolveDestinationPath();
        reporter.info("will be cleaned: %s", destinationPath);
        CommandResult commandResult = new CommandResult();
        File dest = new File(destinationPath);
        if (dest.exists()) {
            try {
                FileUtils.deleteDirectory(dest);
            } catch (IOException e) {
                Throwables.propagate(e);
            }
        }
        boolean stillAlive = dest.exists();
        if (stillAlive) {
            reporter.warn("error deleting directory %s", destinationPath);
        } else {
            reporter.info("successfully deleted directory %s", destinationPath);
        }
        return commandResult;
	}

	@Override
	public String getId() {
		return "clean";
	}

	@Override
	public Object getParametersContainer() {
		return args;
	}
	
	private String resolveDestinationPath() {
        String destinationPath = args.destination;
        if (destinationPath == null) {
            destinationPath = configuration.get("project.destination");
        }
        return destinationPath;
	}

}
