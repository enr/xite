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
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    
    CommandResult execute() {
        ResourceWriter writer = new DefaultResourceWriter(configuration: configuration)
        logger.info("start processing ${paths.sourceDirectory}")
        def phases = ['pre', 'process', 'post']
        def pluginNames = configuration.plugins.enabled
        def PLUGINS_PACKAGE_PREFIX = 'xite.plugin'
        def plugins = pluginNames.collect { pluginName ->
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


