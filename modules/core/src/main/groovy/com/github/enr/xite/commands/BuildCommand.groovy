package com.github.enr.xite.commands;

import javax.inject.Inject

import xite.Paths

import com.github.enr.clap.api.AbstractCommand
import com.github.enr.clap.api.CommandResult
import com.github.enr.clap.api.Configuration
import com.github.enr.clap.api.EnvironmentHolder
import com.github.enr.clap.api.Reporter
import com.github.enr.xite.core.ComponentsLoader
import com.github.enr.xite.plugins.ConfigurationAwareXitePlugin
import com.github.enr.xite.plugins.EnvironmentAwareXitePlugin
import com.github.enr.xite.plugins.PathsAwareXitePlugin
import com.github.enr.xite.plugins.PluginResult
import com.github.enr.xite.plugins.ReporterAwareXitePlugin
import com.github.enr.xite.plugins.XitePlugin

/*
 * Build site.
 */
public class BuildCommand extends AbstractCommand {

    private EnvironmentHolder environment;
    private Configuration configuration;
    private Reporter reporter;

    private BuildCommandArgs args = new BuildCommandArgs();

    @Inject
    public ProcessCommand(EnvironmentHolder environment, Configuration configuration, Reporter reporter) {
        this.environment = environment;
        this.configuration = configuration;
        this.reporter = reporter;
    }

    @Override
    public void init() {
    }

    @Override
    protected CommandResult internalExecute() {
        String sourcePath = argsOrConfiguration(args.source, "project.source")
        String destinationPath = argsOrConfiguration(args.destination, "Project.destination")
        CommandResult commandResult = new CommandResult()
        //ResourceWriter writer = new DefaultResourceWriter(configuration: configuration)
        reporter.out("start processing %s", sourcePath);
        reporter.out("dest = %s", destinationPath);
		configuration.addPath(sourcePath + "/xite/site.groovy")
        //def phases = ['pre', 'process', 'post']
        def pluginNames = configuration.get("plugins.enabled")
		reporter.out("plugins = %s", pluginNames)
        def plugins = pluginNames.collect { pluginName ->
            XitePlugin currentPlugin = ComponentsLoader.pluginForName(pluginName)
            if (currentPlugin) {
                reporter.out("plugin %s loaded", currentPlugin.getClass().getName())
            }
            currentPlugin
        }
        reporter.debug("plugins: %s", plugins)
        for (plugin in plugins) {
            reporter.out(" - execute plugin ${plugin}")
            if (!plugin) continue;
            plugin.setSourcePath(sourcePath)
            plugin.setDestinationPath(destinationPath)
            if (plugin instanceof ConfigurationAwareXitePlugin) {
                ((ConfigurationAwareXitePlugin)plugin).setConfiguration(configuration)
            }
            if (plugin instanceof PathsAwareXitePlugin) {
                ((PathsAwareXitePlugin)plugin).setPaths(new Paths(environment.applicationHome().getAbsolutePath()))
            }
            if (plugin instanceof ReporterAwareXitePlugin) {
                ((ReporterAwareXitePlugin)plugin).setReporter(reporter)
            }
            if (plugin instanceof EnvironmentAwareXitePlugin) {
                ((EnvironmentAwareXitePlugin)plugin).setEnvironment(environment)
            }
            //if (plugin instanceof WriterAwareXitePlugin) ((WriterAwareXitePlugin)plugin).setWriter(writer)
			
			reporter.out(" - apply plugin ${plugin}")
            PluginResult result = plugin.apply()
        }
        return commandResult
    }

    @Override
    public String getId() {
        return "build";
    }

    @Override
    public Object getParametersContainer() {
        return args;
    }

    private <T> T argsOrConfiguration(T arg, String configurationKey) {
        if (arg == null) {
            return configuration.get(configurationKey);
        }
        return arg;
    }
}
