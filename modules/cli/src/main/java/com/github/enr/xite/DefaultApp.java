package com.github.enr.xite;

import javax.inject.Inject;
import javax.inject.Named;


import com.beust.jcommander.JCommander;
import com.github.enr.xite.Configuration;
import com.github.enr.xite.EnvironmentHolder;
import com.github.enr.xite.Reporter;
import com.github.enr.xite.Reporter.Level;
import com.google.common.base.Throwables;


public class DefaultApp implements App {
    
	private Configuration configuration;
    private Reporter reporter;
    private EnvironmentHolder environment;
    
    @Inject @Named("command.main")  private Command mainCommand;
    @Inject @Named("command.build")  private Command buildCommand;
    @Inject @Named("command.run")  private Command runCommand;
    @Inject @Named("command.deploy")  private Command deployCommand;
    @Inject @Named("command.clean")  private Command cleanCommand;

    private MainCommandArgs mainArgs = new MainCommandArgs();
    private BuildCommandArgs buildArgs = new BuildCommandArgs();
    private RunCommandArgs runArgs = new RunCommandArgs();
    private DeployCommandArgs deployArgs = new DeployCommandArgs();
    private CleanCommandArgs cleanArgs = new CleanCommandArgs();
    
    @Inject
    public DefaultApp(EnvironmentHolder environment, Configuration configuration, Reporter reporter) {
    	this.environment = environment;
        this.reporter = reporter;
        this.configuration = configuration;
        this.reporter.debug("Constructor %s", this.getClass().getName());
    }
    
    @Override
    public void run(String[] args) {
    	
        JCommander jc = new JCommander(mainArgs);
        jc.addCommand("build", buildArgs);
        jc.addCommand("run", runArgs);
        jc.addCommand("deploy", deployArgs);
        jc.addCommand("clean", cleanArgs);
        jc.parse(args);
        
        setReportingLevel(reporter, mainArgs);
        
        String commandId = jc.getParsedCommand();

        reporter.info("starting xite with home %s", configuration.applicationHome());
        
        if ((args.length == 0) || (mainArgs.isHelp())) {
        	usageForCommand(jc, commandId);
            systemExit(0);
        }

        reporter.debug(" launch !");
        try {
            CommandResult result = executeCommand(commandId);
            reporter.debug("result = %s", result);
            systemExit(0);
        } catch (Throwable throwable) {
            Throwable cause = Throwables.getRootCause(throwable);
            reporter.warn("something went wrong. catched %s", cause.getClass().getName());
            reporter.warn(cause.getMessage());
        	usageForCommand(jc, commandId);
            systemExit(1);
        }
    }
    
    private void setReportingLevel(Reporter reporter, MainCommandArgs mainArgs) {
        if (mainArgs.isInfo()) {
            reporter.setLevel(Level.INFO);
        }       
        if (mainArgs.isDebug()) {
            reporter.setLevel(Level.DEBUG);
        }
    }
    
    private void usageForCommand(JCommander jc, String commandId) {
        if (commandId == null) {
            jc.usage();
        } else {
            jc.usage(commandId);
        }
    }
    
    /*
     * system exit, but only if environment allowed.
     * probably, this is true in the actual running and false in the acceptance test phase.
     */
    private void systemExit(int value) {
    	if (environment.canExit()) { 
    		System.exit(value);
    	}
    }
    
    private CommandResult executeCommand(String commandId) {
        reporter.debug("command       %s", commandId);
        if ("build".equals(commandId)) { 
            return buildCommand.execute(buildArgs);
        } else if ("run".equals(commandId)) { 
            return runCommand.execute(runArgs);
        } else if ("deploy".equals(commandId)) { 
            return deployCommand.execute(deployArgs);
        } else if ("clean".equals(commandId)) { 
            return cleanCommand.execute(cleanArgs);
        } else { 
            return mainCommand.execute(mainArgs);
        }
    }
}
