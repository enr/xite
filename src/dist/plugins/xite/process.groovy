import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

def paths = binding.getVariable("xite_paths")
def configuration = binding.getVariable("xite_config")
def gse = binding.getVariable('xite_gse')

def logger = LoggerFactory.getLogger('xite.process');





/**
 * A filter for directories not in reserved
 */
// workaround for "Unknown type: METHOD_DEF" error, usyng anonymous class
class NotReservedDirectoryFilter implements FileFilter
{
  /*
   * directory che potrebbero essere presenti in source/ ma non legate a
   * specifici plugin e usabili da tutti
   */
  def reservedDirectories = ['xite', 'templates']
  
  boolean accept(File file) {
    return ((file.isDirectory()) && (!reservedDirectories.contains(file.getName())))
  }
}
def notReservedDirectoryFilter = new NotReservedDirectoryFilter()


logger.debug("starting xite.process")

def processableDirectories = new File(paths.sourceDirectory).listFiles(notReservedDirectoryFilter)
logger.info('processableDirectories: {}', processableDirectories);

logger.info("start processing ${paths.sourceDirectory}")

def phases = ['pre', 'process', 'post']

def plugins = configuration.plugins.enabled

for (phase in phases)
{
  logger.info("running phase ${phase}")
  //for (currentDirectory in processableDirectories)
  for (plugin in plugins)
  {
    //logger.debug("${phase} ] looking for plugin: ${currentDirectory}")
    logger.debug("${phase} ] looking for plugin: ${plugin}")
    /*if (!currentDirectory.exists()) {
      logger.info("skip ${currentDirectory} : not found")
      continue
    }*/
    //def plugin = currentDirectory.name
    def pluginScript = "${plugin}/${phase}.groovy"
    def pluginDirectory = new File(paths.pluginsDirectory, plugin)
    def pluginScriptFile = new File(pluginDirectory, "${phase}.groovy")
    if (!pluginScriptFile.exists()) {
      logger.info("skip plugin script '${pluginScriptFile}': not found")
      continue
    }
    logger.debug("running plugin ${plugin} ${pluginScript}")
    gse.run(pluginScript, binding);
  }
}

