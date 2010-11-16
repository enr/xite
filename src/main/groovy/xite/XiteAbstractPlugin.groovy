package xite

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xite.api.XitePlugin
import xite.api.ConfigurationAwareXitePlugin
import xite.api.PathsAwareXitePlugin
import xite.api.PluginResult

abstract class XiteAbstractPlugin implements XitePlugin, ConfigurationAwareXitePlugin, PathsAwareXitePlugin
{
    Paths paths
    ConfigObject configuration
    
    PluginResult init() {}
    abstract PluginResult apply()
    PluginResult cleanup() {}
}


