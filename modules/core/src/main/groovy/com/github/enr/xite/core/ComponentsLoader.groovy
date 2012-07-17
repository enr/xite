package com.github.enr.xite.core

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.enr.xite.plugins.XitePlugin;

import xite.api.XiteCommand

/**
 * Helper class to load components such as plugins or commands
 */
class ComponentsLoader
{
    
    private static String PLUGINS_PACKAGE_PREFIX = 'com.github.enr.xite.plugins'
    private static String PLUGINS_CLASS_SUFFIX = 'Plugin'
    
    public static XitePlugin pluginForName(String pluginName) {
		if (!pluginName) {
			return null;
		}
		char capital = Character.toUpperCase(pluginName.charAt(0));
        def pluginClassName = "" + capital + pluginName.substring(1, pluginName.length()) + PLUGINS_CLASS_SUFFIX
        def pluginPackage = "${PLUGINS_PACKAGE_PREFIX}" // .${pluginName}"
        def pluginClassFullPath = "${pluginPackage}.${pluginClassName}"
        println("plugin ${pluginName} resolved: ${pluginClassFullPath}")
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
            println("error resolving plugin '${pluginClassFullPath}': ${t.message}")
            throw new RuntimeException(t)
        }
        return plugin
    }
}


