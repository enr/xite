package xite

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xite.api.XitePlugin
import xite.api.ConfigurationAwareXitePlugin
import xite.api.PathsAwareXitePlugin
import xite.api.PluginResult

import xite.api.ResourceWriter;
import xite.api.WriterAwareXitePlugin

abstract class XiteAbstractPlugin implements XitePlugin, ConfigurationAwareXitePlugin, PathsAwareXitePlugin, WriterAwareXitePlugin
{
    Paths paths
    ConfigObject configuration
    ResourceWriter writer
    
    PluginResult init() {
        logger.debug('init()')
    }

    abstract PluginResult apply()

    PluginResult cleanup() {
        logger.debug('cleanup()')
    }
}


