package xite.command

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;

import xite.XiteAbstractCommand
import xite.api.CommandResult

class RunCommand extends XiteAbstractCommand
{
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    
    private static final int DEFAULT_PORT_NUMBER = 8080
    
    private static final String PORT_CONTEXT_KEY = 'xite_port'
    
    CommandResult execute() {
        def port = context.get(PORT_CONTEXT_KEY)
        def resourceBaseDirectoryName = paths.destinationDirectory
        resourceBaseDirectoryName = resourceBaseDirectoryName.substring(0, resourceBaseDirectoryName.lastIndexOf(configuration.app.baseContext));
        logger.debug('port {}', port)
        logger.debug('resourceBaseDirectoryName {}', resourceBaseDirectoryName)
        def resourceBaseDirectory = new File(resourceBaseDirectoryName)
        if (!resourceBaseDirectory.exists()) {
            logger.warn('directory {} not found. exiting', resourceBaseDirectoryName)
            System.exit(1)
        }
        Server server = new Server(port == null ? DEFAULT_PORT_NUMBER : Integer.parseInt(port));
        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        String[] welcomeFiles = [ "index.html" ] as String[]
        resource_handler.setWelcomeFiles(welcomeFiles);
        resource_handler.setResourceBase(resourceBaseDirectoryName);
        logger.info("serving {}", resource_handler.getBaseResource());
        HandlerList handlers = new HandlerList();
        Handler[] hs = [ resource_handler, new DefaultHandler() ] as Handler[]
        handlers.setHandlers(hs);
        server.setHandler(handlers);
        server.start();
        server.join();
    }
}


