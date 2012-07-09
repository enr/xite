package xite;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;


public class ClasspathUtil {

    public static File getClasspathForClass(Class<?> targetClass) {
        URI location = null;
        try {
            location = targetClass.getProtectionDomain().getCodeSource().getLocation().toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        if (!location.getScheme().equals("file")) {
            throw new RuntimeException(String.format("Cannot determine classpath for %s from codebase '%s'.",
                    targetClass.getName(), location));
        }
        return new File(location.getPath());
    }

}