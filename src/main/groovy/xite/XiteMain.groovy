package xite

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xite.UserOptions

import xite.Paths

import xite.api.XiteCommand
import xite.api.CommandResult
import xite.command.CleanCommand
import xite.command.ProcessCommand
import xite.command.RunCommand
import xite.command.DeployCommand

class XiteMain
{
    def logger = LoggerFactory.getLogger('xite.main');
    
    public static void main(String[] args)
    {
        XiteMain xite = new XiteMain()
        int result = xite.process(args)
        System.exit(result)
    }
    
    public int process(String[] args) {
///////////////////////////////////////////////////////// logging initialization
//def loggingConfigurationFile = org.apache.log4j.helpers.Loader.getResource(System.getProperty('log4j.configuration'), Logger.class) 
//logger.debug("Logging started using file ${loggingConfigurationFile}")

/////////////////////////////////////////////////////////////////// user options
def options = new UserOptions(args)
logger.debug("options    : ${options}")

///////////////////////////////////////////////////////////////////////// action
def action = options.action
def runPort = options.port
def doProcess = ((action == 'process') || (action == 'deploy'))
def doDeploy =  (action == 'deploy')
def doRun = (action == 'run')
def doClean = (action == 'clean')

logger.debug("action     : ${action}")
logger.debug("do process : ${doProcess}")
logger.debug("do deploy  : ${doDeploy}")
logger.debug("do run     : ${doRun}")
logger.debug("do clean   : ${doClean}")
logger.debug("runPort    : ${runPort}")

/////////////////////////////////////////////////////////// paths initialization
def xiteHome = System.getProperty('xite.home')
def paths = new Paths(xiteHome)

////////////////////////////////////////////////////////// default configuration
def environment = options.environment
//def defaultConfigurationFile = new File(paths.confDirectory+'/xite-default.groovy')
//ConfigObject configuration = new ConfigSlurper(environment).parse(defaultConfigurationFile.toURL())
String configurationFileBasename = 'xite-default.groovy'
URL configurationUrl = null
if (xiteHome) {
    def defaultConfigurationFile = new File(paths.confDirectory+'/'+configurationFileBasename)
    configurationUrl = defaultConfigurationFile.toURL()
} else {
    // embedded mode
    configurationUrl = this.getClass().getClassLoader().getResource(configurationFileBasename);
}
logger.debug("configurationUrl ${configurationUrl}")
if (configurationUrl == null) {
    throw new RuntimeException("configuration not found")  
}
ConfigObject configuration = new ConfigSlurper(environment).parse(configurationUrl)

/////////////////////////////////////////////////////// resolve source directory
def requiringSourceActions = ['deploy', 'process']
def sourcePath = (options.source) ?: configuration.project.source
def sourceDirectory = new File(sourcePath)
if ((!sourceDirectory.exists()) && (action in requiringSourceActions)) {
    logger.warn("source directory ${sourcePath} not found. exiting")
    System.exit(1)
}
paths.sourceDirectory = paths.normalize(sourceDirectory.absolutePath)
logger.debug("paths.sourceDirectory ${paths.sourceDirectory}")

////////////////////////////////////////////////////// additional configurations
def userHome = System.getProperty('user.home')
def userConfigurationFile = new File("${userHome}/.xite/settings.groovy")
def projectConfigurationFile = new File("${paths.sourceDirectory}/xite/site.groovy")
def configurationFiles = [userConfigurationFile, projectConfigurationFile]
for (configurationFile in configurationFiles) {
  logger.debug("Looking for configuration file ${configurationFile}")
  if (configurationFile.exists()) {
    logger.debug("${configurationFile} found")
    def config = new ConfigSlurper(environment).parse(configurationFile.toURL())
    configuration = (ConfigObject) configuration.merge(config);
  }
}

////////////////////////////////////////////////// resolve destination directory
def destinationPath = (options.destination) ?: configuration.project.destination
def destinationDirectory = new File(destinationPath)
// destination directory should have configuration.app.baseContext as basename
// CLEAR THIS CODE
def d = paths.normalize(destinationDirectory.absolutePath)
if (d.endsWith(configuration.app.baseContext)) {
    paths.destinationDirectory = d
} else {
    paths.destinationDirectory = d + configuration.app.baseContext
}
logger.debug("paths.destinationDirectory ${paths.destinationDirectory}")

//////////////////////////////////////////////////////////////////////// summary
if (logger.isDebugEnabled()) {
    logger.debug("configuration:")
    configuration.each { k, v ->
        logger.debug("${k}:      ${v}")  
    }
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

Map commandContext = [:]
commandContext.put("xite_port", runPort);
CommandResult result = null;
/////////////////////////////////////////////////////////////////// xite process
if (doProcess) {
    result = executeCommand(new ProcessCommand(paths: paths, configuration: configuration))
} else {
    logger.info("skipping action process. Requested action: ${action}")
}

//////////////////////////////////////////////////////////////////// xite deploy
if (doDeploy) {
    result = executeCommand(new DeployCommand(paths: paths, configuration: configuration))
} else {
    logger.info("skipping action deploy. Requested action: ${action}")
}

/////////////////////////////////////////////////////////////////////// xite run
logger.debug(' ::::::::  paths {}', paths)
if (doRun) {
    result = executeCommand(new RunCommand(paths: paths, configuration: configuration, context:commandContext))
} else {
    logger.info("skipping action run. Requested action: ${action}")
}

///////////////////////////////////////////////////////////////////// xite clean
if (doClean) {
    result = executeCommand(new CleanCommand(paths: paths, configuration: configuration))
} else {
    logger.info("skipping action clean. Requested action: ${action}")
}
int exitValue = ((result) ? result.exitValue : 1)
logger.info "process finished with result ${exitValue}"
return exitValue
    }
    
    private CommandResult executeCommand(XiteCommand command) {
        logger.info "executing command ${command}"
        command.init()
        CommandResult result = command.execute()
        command.cleanup()
        return result
    }
}


