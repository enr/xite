package com.github.enr.xite;

import javax.inject.Inject;


import com.github.enr.xite.Configuration;
import com.github.enr.xite.Reporter;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

/*
 * starting from a dataset id, fetches datasources and process items using a pipeline defined in the dataset configuration file
 */
public class BuildCommand extends AbstractCommand {

	private final Reporter reporter;
	private final Configuration configuration;
	
	@Inject
	public BuildCommand(Reporter reporter, Configuration configuration) {
		this.reporter = reporter;
		this.configuration = configuration;
		this.reporter.debug("Command %s", this.getClass().getName());
        this.reporter.debug("configuration %s", this.configuration);
	}
	@Override
	public CommandResult execute(Object args) {
		Preconditions.checkArgument((args instanceof BuildCommandArgs), "expecting %s but was %s", BuildCommandArgs.class, args);
		BuildCommandArgs commandArgs = (BuildCommandArgs) args;
		String source = commandArgs.getSource();
		String destination = commandArgs.getDestination();
        this.reporter.debug("destination %s", destination);
		if (Strings.isNullOrEmpty(source)) {
			reporter.err("source null");
			throw new RuntimeException("dataset null");
		}
        reporter.info("start importing datasetId %s", source);
        return new CommandResult();
	}

}
