
///////////////////////////////////////////////////////// logging initialization
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

def logger = LoggerFactory.getLogger('xite.main');

def loggingConfigurationFile = org.apache.log4j.helpers.Loader.getResource(System.getProperty('log4j.configuration'), Logger.class) 
logger.debug("Logging started using file ${loggingConfigurationFile}")


/////////////////////////////////////////////////////////////////// user options
import xite.UserOptions
def options = new UserOptions(args)


///////////////////////////////////////////////////////////////////////// action
def action = options.action
def runPort = options.port
def doInit = ((action == 'init') || (action == 'process') || (action == 'deploy'))
def doProcess = ((action == 'process') || (action == 'deploy'))
def doDeploy =  (action == 'deploy')
def doRun = (action == 'run')
def doClean = (action == 'clean')

logger.debug("action     : ${action}")
logger.debug("do init    : ${doInit}")
logger.debug("do process : ${doProcess}")
logger.debug("do deploy  : ${doDeploy}")
logger.debug("do run     : ${doRun}")
logger.debug("do clean   : ${doClean}")
logger.debug("runPort    : ${runPort}")


/////////////////////////////////////////////////////////// paths initialization
import xite.Paths
def xiteHome = System.getProperty('xite.home')
def paths = new Paths(xiteHome)


////////////////////////////////////////////////////////// default configuration
def enviroment = options.enviroment
def defaultConfigurationFile = new File(paths.confDirectory+'/xite-default.groovy')
ConfigObject configuration = new ConfigSlurper(enviroment).parse(defaultConfigurationFile.toURL())


/////////////////////////////////////////////////////// resolve source directory
def requiredSourceActions = ['deploy', 'process']
def sourcePath = (options.source) ?: configuration.project.source
def sourceDirectory = new File(sourcePath)
if ((!sourceDirectory.exists()) && (action in requiredSourceActions)) {
    logger.warn("source directory ${sourcePath} not found. exiting")
    System.exit(1)
}

paths.sourceDirectory = paths.normalize(sourceDirectory.absolutePath)


////////////////////////////////////////////////////// additional configurations
def userHome = System.getProperty('user.home')
def userConfigurationFile = new File("${userHome}/.xite/settings.groovy")
def projectConfigurationFile = new File("${paths.sourceDirectory}/xite/site.groovy")
def configurationFiles = [userConfigurationFile, projectConfigurationFile]

for (configurationFile in configurationFiles) {
  logger.debug("Looking for configuration file ${configurationFile}")
  if (configurationFile.exists()) {
    logger.debug("${configurationFile} found")
    def config = new ConfigSlurper(enviroment).parse(configurationFile.toURL())
    configuration = (ConfigObject) configuration.merge(config);
  }
}


////////////////////////////////////////////////// resolve destination directory
def destinationPath = (options.destination) ?: configuration.project.destination
def destinationDirectory = new File(destinationPath)
paths.destinationDirectory = paths.normalize(destinationDirectory.absolutePath)


//////////////////////////////////////////////////////////////////////// summary
if (logger.isDebugEnabled()) {
    logger.debug("configuration:")
    configuration.each { k, v ->
        logger.debug("${k}:      ${v}")  
    }
    logger.debug("urls in classpath:")
    def cl = this.class.classLoader
    def loader = cl.rootLoader
    for (u in loader.getURLs()){
        logger.debug("${u}")
    }
    logger.debug('paths: {}', paths)
}


////////////////////////////////////////////// GroovyScriptEngine initialization
def roots = [ paths.pluginsDirectory ] as String[]
logger.info('roots: {}', roots)
GroovyScriptEngine gse = new GroovyScriptEngine(roots);


//////////////////////////////////////////////////////////////// binding setting
Binding binding = new Binding();
binding.setVariable("xite_paths", paths);
binding.setVariable("xite_debug", false);
binding.setVariable("xite_config", configuration);
binding.setVariable("xite_gse", gse);

///////////////////////////////////////////////////////////// additional options
binding.setVariable("xite_option_port", runPort);


////////////////////////////////////////////////////////////////////// xite init
if (doInit) {
  logger.info("starting xite.init")
  gse.run('xite/init.groovy', binding);
} else {
  logger.info("skipping action init. Requested action: ${action}")
}


/////////////////////////////////////////////////////////////////// xite process
if (doProcess) {
  logger.info("starting xite.process")
  gse.run('xite/process.groovy', binding);
} else {
  logger.info("skipping action process. Requested action: ${action}")
}


//////////////////////////////////////////////////////////////////// xite deploy
if (doDeploy) {
  logger.info("starting xite.deploy")
  gse.run('xite/deploy.groovy', binding);
} else {
  logger.info("skipping action deploy. Requested action: ${action}")
}


/////////////////////////////////////////////////////////////////////// xite run
if (doRun) {
  logger.info("starting xite.run")
  gse.run('xite/run.groovy', binding);
} else {
  logger.info("skipping action run. Requested action: ${action}")
}


///////////////////////////////////////////////////////////////////// xite clean
if (doClean) {
  logger.info("starting xite.clean")
  gse.run('xite/clean.groovy', binding);
} else {
  logger.info("skipping action clean. Requested action: ${action}")
}


////////////////////////////////////////////////////////////// exit and clean up
System.exit(0)

