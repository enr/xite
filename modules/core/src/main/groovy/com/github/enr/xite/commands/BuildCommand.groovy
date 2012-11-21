package com.github.enr.xite.commands;

import java.io.File
import java.io.FilenameFilter
import java.nio.charset.Charset;
import java.util.List

import javax.inject.Inject

import com.github.enr.clap.api.AbstractCommand
import com.github.enr.clap.api.CommandResult
import com.github.enr.clap.api.Configuration
import com.github.enr.clap.api.EnvironmentHolder
import com.github.enr.clap.api.Reporter
import com.github.enr.xite.plugins.ConfigurationAwareXitePlugin
import com.github.enr.xite.plugins.EnvironmentAwareXitePlugin
import com.github.enr.xite.plugins.PluginResult
import com.github.enr.xite.plugins.ReporterAwareXitePlugin
import com.github.enr.xite.plugins.XitePlugin
import com.github.enr.xite.util.ComponentsLoader
import com.github.enr.xite.util.Directories
import com.github.enr.xite.util.FilePaths
import com.google.common.base.Charsets;
import com.google.common.io.Files;

/**
 * Build site.
 */
public class BuildCommand extends AbstractCommand {

    private EnvironmentHolder environment;
    private Configuration configuration;
    private Reporter reporter;

    private BuildCommandArgs args = new BuildCommandArgs();
    
    private static final FilenameFilter FILTERABLE_FILENAMES = new FilenameFilter() {
        public boolean accept(File dir, String name) {
            return (name.endsWith(".html") || name.endsWith(".js") || name.endsWith(".css"));
        }
    }

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
        CommandResult commandResult = new CommandResult()
        //ResourceWriter writer = new DefaultResourceWriter(configuration: configuration)
        String sourcePath = argsOrConfiguration(args.source, "project.source")
        reporter.debug("start processing %s", sourcePath);
		configuration.addPath(sourcePath + "/xite/site.groovy")
		
        String destinationPath = argsOrConfiguration(args.destination, "project.destination")
        reporter.debug("required destination %s", destinationPath);
		if (destinationPath == null || destinationPath.length() == 0) {
			commandResult.failWithMessage("destination path should not be null");
			return commandResult;
		}
		def context = configuration.get("app.baseContext")
		destinationPath = (destinationPath.endsWith(context) ? destinationPath : destinationPath+context);
        reporter.debug("using destination %s", destinationPath);
        //def phases = ['pre', 'process', 'post']
        def pluginNames = configuration.get("plugins.enabled")
		reporter.debug("plugins = %s", pluginNames)
        def plugins = pluginNames.collect { pluginName ->
            XitePlugin currentPlugin = ComponentsLoader.pluginForName(pluginName)
            if (currentPlugin) {
                reporter.debug("plugin %s loaded", currentPlugin.getClass().getName())
            }
            currentPlugin
        }
        reporter.debug("plugins: %s", plugins)
        for (plugin in plugins) {
            if (!plugin) continue;
            plugin.setSourcePath(sourcePath)
            plugin.setDestinationPath(destinationPath)
            if (plugin instanceof ConfigurationAwareXitePlugin) {
                ((ConfigurationAwareXitePlugin)plugin).setConfiguration(configuration)
            }
            //if (plugin instanceof PathsAwareXitePlugin) {
            //    ((PathsAwareXitePlugin)plugin).setPaths(new Paths(environment.applicationHome().getAbsolutePath()))
            //}
            if (plugin instanceof ReporterAwareXitePlugin) {
                ((ReporterAwareXitePlugin)plugin).setReporter(reporter)
            }
            if (plugin instanceof EnvironmentAwareXitePlugin) {
                ((EnvironmentAwareXitePlugin)plugin).setEnvironment(environment)
            }
            //if (plugin instanceof WriterAwareXitePlugin) ((WriterAwareXitePlugin)plugin).setWriter(writer)
			reporter.debug(" - apply plugin ${plugin}")
            PluginResult result = plugin.apply()
        }

        filterProperties(sourcePath, destinationPath);
        
        return commandResult
    }
    
    private void filterProperties(String sourcePath, String destinationPath) {
        def processResources = configuration.get('properties.filter.enabled')
        if (!processResources) {
            reporter.debug("skipping filter properties")
            return;
        }
        
        def encoding = configuration.get("app.encoding")
        
        def resourcesFiltersFile = configuration.get('properties.filter.file')

        def prefix =  configuration.get('properties.filter.prefix')
        def suffix =  configuration.get('properties.filter.suffix')

        def substitutionsFilePath = FilePaths.join(sourcePath, resourcesFiltersFile)

        File substitutionsFile = new File(substitutionsFilePath)
        Properties subs = new Properties();
        if (substitutionsFile.isFile()) {
            FileInputStream inp = new FileInputStream(substitutionsFilePath);
            subs.load(inp);
            inp.close();
        }
        subs.setProperty("app.baseContext", configuration.get("app.baseContext"))

        List<File> filterables = Directories.list(new File(destinationPath), FILTERABLE_FILENAMES, true);
        
        for (File file : filterables) {
            String raw = Files.toString(file, Charset.forName(encoding));
            String filtered = raw
            subs.each() { key, value ->
                def placeholder = "${prefix}${key}${suffix}"
                filtered = filtered.replace(placeholder, value)
            }
            Files.write(filtered, file, Charset.forName(encoding));
        }
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
