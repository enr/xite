package com.github.enr.xite;

import java.io.File;

import javax.inject.Inject;

/*
 * default implementation for environment holder.
 * this class is used as extension-point from the environment holder used in acceptance tests.
 */
public class DefaultEnvironmentHolder implements EnvironmentHolder {

    private Reporter reporter;
    
    private String applicationName = "xite";
    private File home;
    
    /*
     * FIXME take version dinamically from MANIFEST
     */
    private String applicationVersion = "0.1.alpha";
    
    private final String os = System.getProperty("os.name").toLowerCase();
    
    @Inject
    public DefaultEnvironmentHolder(Reporter reporter) {
    	this.reporter = reporter;
        File location = ClasspathUtil.getClasspathForClass(DefaultEnvironmentHolder.class);
        if (home == null) {
            home = location.getParentFile().getParentFile();
        }
        this.reporter.debug("home = %s", home);
	}
    
	@Override
	public File applicationHome() {
		return home;
	}
	
    public boolean isWindows() {
        return (os.indexOf("win") >= 0);
    }

    public boolean isMac() {
        return (os.indexOf("mac") >= 0);
    }

    public boolean isUnix() {
        return (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0);
    }

	@Override
	public File installationConfigurationDirectory() {
		return new File(home, "conf");
	}

	@Override
	public File systemConfigurationDirectory() {
		File directory = null;
        if (isWindows()) {
            directory = new File("c:/"+applicationName+"/" + applicationVersion);
        } else {
        	directory = new File("/etc/"+applicationName+"/" + applicationVersion);
        }
        return directory;
	}

	@Override
	public File userConfigurationDirectory() {
        StringBuilder sb = new StringBuilder().append(System.getProperty("user.home")).append(File.separator)
                .append(".").append(applicationName).append(File.separator).append(applicationVersion);
        return new File(sb.toString());
	}

	@Override
	public boolean canExit() {
		return true;
	}

}
