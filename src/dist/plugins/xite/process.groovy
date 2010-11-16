import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xite.api.XitePlugin;

import org.apache.commons.lang.StringUtils;

def paths = binding.getVariable("xite_paths")
def configuration = binding.getVariable("xite_config")
def gse = binding.getVariable('xite_gse')

logger = LoggerFactory.getLogger('xite.process');

logger.debug("starting xite.process")

logger.info("start processing ${paths.sourceDirectory}")

def phases = ['pre', 'process', 'post']

def pluginNames = configuration.plugins.enabled
def PLUGINS_PACKAGE_PREFIX = 'xite.plugin'
def plugins = pluginNames.collect { pluginName ->
    def pluginClassName = StringUtils.capitalize(pluginName) + 'Plugin'
    def pluginPackage = "${PLUGINS_PACKAGE_PREFIX}.${pluginName}"
    def pluginClassFullPath = "${pluginPackage}.${pluginClassName}"
    logger.debug("plugin ${pluginName} resolved: ${pluginClassFullPath}")
    def currentPlugin = loadPlugin(pluginClassFullPath)
    if (currentPlugin) {
        logger.debug("plugin ${currentPlugin.class} loaded")
    }
    currentPlugin
}

for (phase in phases)
{
  logger.info("running phase ${phase}")
  //for (currentDirectory in processableDirectories)
  for (plugin in pluginNames)
  {
    //logger.debug("${phase} ] looking for plugin: ${currentDirectory}")
    logger.debug("${phase} ] looking for plugin: ${plugin}")
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

def XitePlugin loadPlugin(String pluginClassFullPath)
{
    XitePlugin plugin
     try {
      Class<?> clazz = Class.forName(pluginClassFullPath);
      Object o = clazz.newInstance(); // InstantiationException
      if (o instanceof XitePlugin)
      {
         plugin = (XitePlugin) o   
      }

      // production code should handle these exceptions more gracefully
    } catch (Throwable t) {
      logger.warn("error resolving plugin '${pluginClassFullPath}': ${t.message}")
    }
    return plugin
}

