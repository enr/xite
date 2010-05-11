import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

 
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
//import org.eclipse.jetty.util.log.Log;


def paths = binding.getVariable("xite_paths")
def configuration = binding.getVariable("xite_config")
def port = binding.getVariable("xite_option_port")

String[] args = [] as String[]

def logger = LoggerFactory.getLogger('xite.run');

logger.info("starting xite.run")

Server server = new Server(port == null?8080:Integer.parseInt(port));

ResourceHandler resource_handler = new ResourceHandler();
resource_handler.setDirectoriesListed(true);
String[] welcomeFiles = [ "index.html" ] as String[]
resource_handler.setWelcomeFiles(welcomeFiles);

resource_handler.setResourceBase(args.length == 2?args[1]:"./target/xite");
logger.info("serving {}", resource_handler.getBaseResource());

HandlerList handlers = new HandlerList();

Handler[] hs = [ resource_handler, new DefaultHandler() ] as Handler[]

handlers.setHandlers(hs);
server.setHandler(handlers);

server.start();
server.join();

