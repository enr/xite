import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;

def logger = LoggerFactory.getLogger('xite.run');
logger.info("starting xite.run")

def paths = binding.getVariable("xite_paths")
def configuration = binding.getVariable("xite_config")
def port = binding.getVariable("xite_option_port")

def resourceBaseDirectoryName = paths.destinationDirectory

resourceBaseDirectoryName = resourceBaseDirectoryName.substring(0, resourceBaseDirectoryName.lastIndexOf(configuration.app.baseContext));

logger.debug('port {}', port)
logger.debug('resourceBaseDirectoryName {}', resourceBaseDirectoryName)

def resourceBaseDirectory = new File(resourceBaseDirectoryName)
if (!resourceBaseDirectory.exists()) {
    logger.warn('directory {} not found. exiting', resourceBaseDirectoryName)
    System.exit(1)
}

Server server = new Server(port == null ? 8080 : Integer.parseInt(port));

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

