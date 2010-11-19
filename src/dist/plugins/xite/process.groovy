import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xite.api.ResourceWriter;
import xite.api.XitePlugin;
import xite.api.ConfigurationAwareXitePlugin
import xite.api.PathsAwareXitePlugin
import xite.api.WriterAwareXitePlugin
import xite.api.PluginResult
import xite.Paths
import xite.DefaultResourceWriter

import org.apache.commons.lang.StringUtils;

logger = LoggerFactory.getLogger('xite.process');

def paths = binding.getVariable("xite_paths")
def configuration = binding.getVariable("xite_config")
def gse = binding.getVariable('xite_gse')

ResourceWriter writer = new DefaultResourceWriter(configuration: configuration)

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
    XitePlugin currentPlugin = loadPlugin(pluginClassFullPath)
    if (currentPlugin) {
        logger.debug("plugin ${currentPlugin.class} loaded")
    }
    currentPlugin
}

    logger.warn("plugins: ${plugins}")

for (plugin in plugins) {
    logger.warn(" - execute plugin ${plugin}")
    if (!plugin) continue;
    if (plugin instanceof ConfigurationAwareXitePlugin) ((ConfigurationAwareXitePlugin)plugin).setConfiguration(configuration)
    if (plugin instanceof PathsAwareXitePlugin) ((PathsAwareXitePlugin)plugin).setPaths(paths)
    if (plugin instanceof WriterAwareXitePlugin) ((WriterAwareXitePlugin)plugin).setWriter(writer)
    PluginResult result = plugin.apply()
}

//////////for (phase in phases)
//////////{
//////////  logger.debug("running phase ${phase}")
//////////  //for (currentDirectory in processableDirectories)
//////////  for (plugin in pluginNames)
//////////  {
//////////    //logger.debug("${phase} ] looking for plugin: ${currentDirectory}")
//////////    logger.debug("${phase} ] looking for plugin: ${plugin}")
//////////    def pluginScript = "${plugin}/${phase}.groovy"
//////////    def pluginDirectory = new File(paths.pluginsDirectory, plugin)
//////////    def pluginScriptFile = new File(pluginDirectory, "${phase}.groovy")
//////////    if (!pluginScriptFile.exists()) {
//////////      logger.info("skip plugin script '${pluginScriptFile}': not found")
//////////      continue
//////////    }
//////////    logger.debug("running plugin ${plugin} ${pluginScript}")
//////////    gse.run(pluginScript, binding);
//////////  }
//////////}


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

