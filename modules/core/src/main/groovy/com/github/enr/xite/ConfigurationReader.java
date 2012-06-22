package com.github.enr.xite;

import java.net.URL;

public interface ConfigurationReader {
	
	/**
	 * Adds a new configuration resource.
	 * 
	 * @param configuration
	 */
    void addConfiguration(URL configuration);
    
    /**
     * Adds a new binding ie a variable which name will be evaluated reading the configuration script
     */
    void addBinding(String key, Object reference);
    
    /**
     * Clear all configuration data.
     */
    void reset();

    /**
     * Builds configuration data.
     * 
     * @return if method complete succesfully
     */
    boolean build();
    
    /**
     * Builds configuration data for the given enviroment.
     * 
     * @return if method complete succesfully
     */
    boolean build(String enviroment);
    
    /*
     * Usable in edge cases, ie ...
     * 
     * @param <T>
     * @param key
     * @param type
     * @return a typed instance for the given key or null, if key not found
     */
    //<T> T get(String key, Class<T> type);
    
    /**
     * Get a configuration value
     *
     * @param key
     * @return the value of the specified key, implicitally casted as required or null if something goes wrong
     */
    <T> T get(String key);

    /*
     * 
     * @param prefix
     * @return a map of configurations data with key starting with prefix
     * 
     */
    //Map<String, Object> getBulk(String prefix);
    
    //Map<String, Object> getBulk(String prefix, ConfigObject configObject);

	//void parseAppConfiguration(String appConfigurationPath);

	//void parseDatasetConfiguration(String datasetConfigurationPath);

}
