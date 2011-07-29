package xite

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xite.api.XitePlugin
import xite.api.XiteCommand

import org.apache.commons.lang.StringUtils;

/**
 * Helper class to load components such as plugins or commands
 */
class ComponentsLoader
{
    private static logger = LoggerFactory.getLogger(ComponentsLoader.class.name);
    
    private static String PLUGINS_PACKAGE_PREFIX = 'xite.plugin'
    private static String PLUGINS_CLASS_SUFFIX = 'Plugin'
    
    public static XitePlugin pluginForName(String pluginName) {
        def pluginClassName = StringUtils.capitalize(pluginName) + PLUGINS_CLASS_SUFFIX
        def pluginPackage = "${PLUGINS_PACKAGE_PREFIX}.${pluginName}"
        def pluginClassFullPath = "${pluginPackage}.${pluginClassName}"
        logger.debug("plugin ${pluginName} resolved: ${pluginClassFullPath}")
        XitePlugin plugin = pluginForClassName(pluginClassFullPath)
        return plugin
    }
    
    public static XiteCommand commandForName(String commandName) {
    
    }

    public static XitePlugin pluginForClassName(String pluginClassFullPath)
    {
        XitePlugin plugin
        try {
            Class<?> clazz = Class.forName(pluginClassFullPath, false, ComponentsLoader.class.getClassLoader());
            Object o = clazz.newInstance();
            if (o instanceof XitePlugin)
            {
                plugin = (XitePlugin) o
            }
        } catch (Throwable t) {
            logger.warn("error resolving plugin '${pluginClassFullPath}': ${t.message}")
            throw new RuntimeException(t)
        }
        return plugin
    }
}


