package com.github.enr.xite.commands;

import java.io.File;

import javax.inject.Inject;

import com.github.enr.clap.api.AbstractCommand;
import com.github.enr.clap.api.CommandResult;
import com.github.enr.clap.api.Configuration;
import com.github.enr.clap.api.EnvironmentHolder;
import com.github.enr.clap.api.Reporter;
import com.github.enr.xite.util.Directories;

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
		/*
        String sourcePath = argsOrConfiguration(args.source, "project.source")
        reporter.debug("start processing %s", sourcePath);
		configuration.addPath(sourcePath + "/xite/site.groovy")
		
        String destinationPath = argsOrConfiguration(args.destination, "project.destination")
        reporter.debug("required destination %s", destinationPath);
		if (destinationPath == null || destinationPath.length() == 0) {
			commandResult.failWithMessage("destination path should not be null");
			return commandResult;
		}
        */
        String destinationPath = resolveDestinationPath();
        reporter.out("will be cleaned: %s", destinationPath);
        CommandResult commandResult = new CommandResult();
        File dest = new File(destinationPath);
        if (dest.exists()) {
            Directories.delete(dest);
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
