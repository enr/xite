package com.github.enr.xite;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;

/*
 * utility class pertaining classpath.
 */
public class ClasspathUtil {

    public static File getClasspathForClass(Class<?> targetClass) {
        URI location = null;
        try {
            location = targetClass.getProtectionDomain().getCodeSource().getLocation().toURI();
        } catch (URISyntaxException e) {
            Throwables.propagate(e);
        }
        if (!location.getScheme().equals("file")) {
            throw new RuntimeException(String.format("Cannot determine classpath for %s from codebase '%s'.",
                    targetClass.getName(), location));
        }
        return new File(location.getPath());
    }
    

    /**
     * Returns a list of jars urls to include in Please classpath.
     * 
     * @return a list of jars urls to include in Please classpath.
     *
    public static List<URL> getPlease_ClasspathUrls() {
        return getPleaseClasspathUrls(getPleaseHome());
    }
    */

    /**
     * Returns a list of jars urls to include in the app classpath, using the
     * given file as home.
     * 
     * @param home
     * @return a list of jars urls to include in Please classpath.
     */
    public static List<URL> getApplicationClasspathUrls(File home) {
        List<URL> result = new ArrayList<URL>();
        result.addAll(jarsInSubDirectory(home, "lib"));
        result.addAll(jarsInSubDirectory(home, "plugins"));
        return result;
    }

    /*
    private static File getPleaseHome() {
        File codeSource = ClasspathUtil.getClasspathForClass(ProcessBootstrap.class);
        File pleaseHome = null;
        if (codeSource.isFile()) {
            // Loaded from a JAR - assume we're running from the distribution
            pleaseHome = codeSource.getParentFile().getParentFile();
        } else {
            // Loaded from a classes dir - assume we're running from the ide or
            // tests
            pleaseHome = null;
        }
        return pleaseHome;
    }
    */

    private static List<URL> jarsInSubDirectory(File parent, String dirName) {
        parent = Preconditions.checkNotNull(parent, "asking jars, parent directory cannot be null");
        dirName = Preconditions.checkNotNull(dirName, "asking jars, directory cannot be null");
        Preconditions.checkArgument(parent.exists(), "parent directory '%s' not found", parent.getAbsolutePath());
        Preconditions.checkArgument(parent.isDirectory(), "parent directory '%s' not a directory",
                parent.getAbsolutePath());
        String pleaseHomePath = parent.getAbsolutePath();
        List<URL> plugins = new ArrayList<URL>();
        File subDir = new File(pleaseHomePath, dirName);

        if ((subDir == null) || (!subDir.exists()) || (!subDir.isDirectory())) {
            return plugins;
        }
        for (File file : subDir.listFiles()) {
            if (!file.getName().endsWith(".jar")) {
                continue;
            }
            try {
                URL url = file.toURI().toURL();
                plugins.add(url);
            } catch (MalformedURLException e) {
                throw new RuntimeException("error loading jar " + file.getAbsolutePath());
            }
        }
        return plugins;
    }

}
