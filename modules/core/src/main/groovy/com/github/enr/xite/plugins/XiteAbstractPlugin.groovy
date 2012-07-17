package com.github.enr.xite.plugins

import com.github.enr.clap.api.Configuration
import com.github.enr.clap.api.EnvironmentHolder
import com.github.enr.clap.api.Reporter

abstract class XiteAbstractPlugin implements XitePlugin, ConfigurationAwareXitePlugin, EnvironmentAwareXitePlugin, ReporterAwareXitePlugin
{

    Configuration configuration
    EnvironmentHolder environment
    Reporter reporter
    
    String sourcePath
    String destinationPath    
    
    PluginResult init() {
        println('init()')
    }

    abstract PluginResult apply()

    PluginResult cleanup() {
        println('cleanup()')
    }
}


