package com.github.enr.xite.commands;

import javax.inject.Inject

import org.eclipse.jetty.server.Handler
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.handler.DefaultHandler
import org.eclipse.jetty.server.handler.HandlerList
import org.eclipse.jetty.server.handler.ResourceHandler

import com.github.enr.clap.api.AbstractCommand
import com.github.enr.clap.api.CommandResult
import com.github.enr.clap.api.Configuration
import com.github.enr.clap.api.EnvironmentHolder
import com.github.enr.clap.api.Reporter

/*
 * Run the produced site.
 */
public class ServeCommand extends AbstractCommand {
	
	private EnvironmentHolder environment;
	private Configuration configuration;
	private Reporter reporter;

	private ServeCommandArgs args = new ServeCommandArgs();
	
	@Inject
	public RunCommand(EnvironmentHolder environment, Configuration configuration, Reporter reporter) {
		this.environment = environment;
		this.configuration = configuration;
		this.reporter = reporter;
	}

	@Override
	public void init() {
		
	}

	@Override
	protected CommandResult internalExecute() {
        String rootPath = argsOrConfiguration(args.root, "project.destination")
		reporter.debug("port = %d", args.port);
		reporter.debug("root directory = %s", rootPath);
        CommandResult commandResult = new CommandResult()
        def port = args.port
		if (rootPath == null || rootPath.length() == 0) {
            reporter.warn('directory %s not found. exiting', rootPath)
            commandResult.failWithMessage("directory not found")
            commandResult.setExitValue(1)
            return commandResult
		}
        def resourceBaseDirectoryName = rootPath
        resourceBaseDirectoryName = resourceBaseDirectoryName.substring(0, resourceBaseDirectoryName.lastIndexOf(configuration.get("app.baseContext")));
        reporter.debug('resourceBaseDirectoryName %s', resourceBaseDirectoryName)
        def resourceBaseDirectory = new File(resourceBaseDirectoryName)
        if (!resourceBaseDirectory.exists()) {
            reporter.warn('directory %s not found. exiting', resourceBaseDirectoryName)
            commandResult.failWithMessage("directory not found")
            commandResult.setExitValue(1)
            return commandResult
        }
		
		def context = configuration.get("app.baseContext")
		
        Server server = new Server(port);

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        String[] welcomeFiles = [ "index.html" ] as String[]
        resourceHandler.setWelcomeFiles(welcomeFiles);
        resourceHandler.setResourceBase(resourceBaseDirectoryName);
        reporter.info("serving %s", resourceHandler.getBaseResource());
        HandlerList handlers = new HandlerList();
        Handler[] hs = [ resourceHandler, new DefaultHandler() ] as Handler[]
        handlers.setHandlers(hs);
        server.setHandler(handlers);
        server.start();
        server.join();

        return commandResult
	}

	@Override
	public String getId() {
		return "serve";
	}

	@Override
	public Object getParametersContainer() {
		return args;
	}

    private <T> T argsOrConfiguration(T arg, String configurationKey) {
        if (arg == null) {
            return configuration.get(configurationKey);
        }
        return arg;
    }

}
