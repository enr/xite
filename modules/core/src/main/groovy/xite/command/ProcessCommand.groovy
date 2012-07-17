package xite.command

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.github.enr.xite.core.ComponentsLoader;
import com.github.enr.xite.plugins.ConfigurationAwareXitePlugin;
import com.github.enr.xite.plugins.PathsAwareXitePlugin;
import com.github.enr.xite.plugins.PluginResult;
import com.github.enr.xite.plugins.XitePlugin;

import xite.DefaultResourceWriter
import xite.Paths
import xite.XiteAbstractCommand
import xite.api.CommandResult
import xite.api.ResourceWriter
import xite.api.WriterAwareXitePlugin

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
        CommandResult commandResult = new CommandResult()
        ResourceWriter writer = new DefaultResourceWriter(configuration: configuration)
        logger.info("start processing ${paths.sourceDirectory}")
        //def phases = ['pre', 'process', 'post']
        def pluginNames = configuration.plugins.enabled
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
        return commandResult
    }

}


