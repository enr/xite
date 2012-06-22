package com.github.enr.xite;

import java.io.File;
import java.net.MalformedURLException;

import javax.inject.Inject;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;

public class DefaultConfiguration implements Configuration {
    
    private ConfigurationReader configurationReader;
    private Reporter reporter;
    private EnvironmentHolder environment;
    
    private static final String CONFIGURATION_FILENAME = "xite-settings.groovy";
    
    @Inject
    public DefaultConfiguration(ConfigurationReader configurationReader, Reporter reporter, EnvironmentHolder environment) {
        this.configurationReader = configurationReader;
        this.reporter = reporter;
        this.environment = environment;
        this.reporter.info("configurationReader = %s", this.configurationReader);
        init();
    }
    
    private void init() {
    	String installationConfigurationPath = installationConfigurationPath();
    	String systemConfigurationPath = systemConfigurationPath();
    	String userConfigurationPath = userConfigurationPath();
        this.reporter.out("installationConfigurationPath = %s", installationConfigurationPath);
        this.reporter.out("systemConfigurationPath = %s", systemConfigurationPath);
        this.reporter.out("userConfigurationPath = %s", userConfigurationPath);
        load(installationConfigurationPath);
        load(systemConfigurationPath);
        load(userConfigurationPath);
        configurationReader.build();
        reporter.out("a = %s", configurationReader.get("a"));
        reporter.out("b.c = %s", configurationReader.get("b.c"));
    }

    /*
     * returns the path to the main configuration file for the given installation
     */
    private String installationConfigurationPath() {
    	File configurationDirectory = environment.installationConfigurationDirectory();
        StringBuilder sb = new StringBuilder().append(configurationDirectory).append(File.separator).append(CONFIGURATION_FILENAME);
        return sb.toString();
	}
    
    /*
     * returns the path to the main configuration file for the system where Pick is running
     */
    private String systemConfigurationPath() {
    	File configurationDirectory = environment.systemConfigurationDirectory();
        StringBuilder sb = new StringBuilder().append(configurationDirectory).append(File.separator).append(CONFIGURATION_FILENAME);
        return sb.toString();
	}
    
    /*
     * returns the path to the main configuration file for the user launching Pick
     */
    private String userConfigurationPath() {
    	File configurationDirectory = environment.userConfigurationDirectory();
        StringBuilder sb = new StringBuilder().append(configurationDirectory).append(File.separator).append(CONFIGURATION_FILENAME);
        return sb.toString();
	}
    
    private void load(String path) {
    	Preconditions.checkNotNull(path);
    	File configurationFile = new File(path);
    	//Preconditions.checkArgument(configurationFile.exists(), "configuration %s not found", path);
    	if (!configurationFile.exists()) {
    		reporter.out("configuration %s not found", path);
    		return;
    	}
    	try {
			configurationReader.addConfiguration(configurationFile.toURI().toURL());
		} catch (MalformedURLException e) {
			Throwables.propagate(e);
		}
    }

	@Override
	public File applicationHome() {
		return environment.applicationHome();
	}

}
