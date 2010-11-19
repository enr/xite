package xite.command

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xite.XiteAbstractCommand
import xite.api.CommandResult

import xite.api.ResourceWriter;
import xite.api.XitePlugin;
import xite.api.ConfigurationAwareXitePlugin
import xite.api.PathsAwareXitePlugin
import xite.api.WriterAwareXitePlugin
import xite.api.PluginResult
import xite.Paths
import xite.DefaultResourceWriter

import xite.ComponentsLoader

/**
 * The command executed by default.
 *
 * It resolves which plugins are to be applied and execute them.
 *
 */
class ProcessCommand extends XiteAbstractCommand
{
    CommandResult execute() {
ResourceWriter writer = new DefaultResourceWriter(configuration: configuration)

logger.debug("starting xite.process")

logger.info("start processing ${paths.sourceDirectory}")

def phases = ['pre', 'process', 'post']

def pluginNames = configuration.plugins.enabled
def PLUGINS_PACKAGE_PREFIX = 'xite.plugin'
def plugins = pluginNames.collect { pluginName ->
//////def pluginClassName = StringUtils.capitalize(pluginName) + 'Plugin'
//////def pluginPackage = "${PLUGINS_PACKAGE_PREFIX}.${pluginName}"
//////def pluginClassFullPath = "${pluginPackage}.${pluginClassName}"
//////logger.debug("plugin ${pluginName} resolved: ${pluginClassFullPath}")
//////XitePlugin currentPlugin = loadPlugin(pluginClassFullPath)
    XitePlugin currentPlugin = ComponentsLoader.pluginForName(pluginName)
    if (currentPlugin) {
        logger.debug("plugin ${currentPlugin.class} loaded")
    }
    currentPlugin
}

logger.debug("plugins: ${plugins}")

for (plugin in plugins) {
    logger.debug(" - execute plugin ${plugin}")
    if (!plugin) continue;
    if (plugin instanceof ConfigurationAwareXitePlugin) ((ConfigurationAwareXitePlugin)plugin).setConfiguration(configuration)
    if (plugin instanceof PathsAwareXitePlugin) ((PathsAwareXitePlugin)plugin).setPaths(paths)
    if (plugin instanceof WriterAwareXitePlugin) ((WriterAwareXitePlugin)plugin).setWriter(writer)
    PluginResult result = plugin.apply()
}

    }

}


