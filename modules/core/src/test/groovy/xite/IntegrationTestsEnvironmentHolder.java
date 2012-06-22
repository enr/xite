package xite;

import java.io.File;

import javax.inject.Inject;

import com.github.enr.xite.ClasspathUtil;
import com.github.enr.xite.DefaultEnvironmentHolder;
import com.github.enr.xite.EnvironmentHolder;
import com.github.enr.xite.Reporter;


public class IntegrationTestsEnvironmentHolder implements EnvironmentHolder {
    private Reporter reporter;
    
    private String applicationName = "xite";
    private File home;
    
    /*
     * FIXME take version dinamically from MANIFEST
     */
    private String applicationVersion = "0.1.alpha";
    
    @Inject
    public IntegrationTestsEnvironmentHolder(Reporter reporter) {
    	this.reporter = reporter;
        File location = ClasspathUtil.getClasspathForClass(DefaultEnvironmentHolder.class);
        if (home == null) {
            home = location.getParentFile().getParentFile().getParentFile().getParentFile().getParentFile();
        }
        this.reporter.debug("home = %s", home);
	}
    
	@Override
	public File applicationHome() {
		return home;
	}

	@Override
	public File installationConfigurationDirectory() {
		return new File(home, "conf");
	}

	@Override
	public File systemConfigurationDirectory() {
		String systemConfigurationPath = home.getAbsolutePath() + File.pathSeparator + "modules";
		File directory = new File(systemConfigurationPath);
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
