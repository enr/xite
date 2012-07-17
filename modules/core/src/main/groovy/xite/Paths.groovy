package xite

import java.io.File;

import com.google.common.io.Files;

/**
 * Helper class to resolve and operate on the paths needed from Xite
 */
class Paths
{
    String xiteHome
    String binDirectory
    String confDirectory
    String pluginsDirectory
    String libDirectory

    /**
     * Source and destination directories are not resolved but should be setted in
     * from the application code.
     */
    String sourceDirectory
    String destinationDirectory
  
    def Paths(String xHome)
    {
        xiteHome = normalize(xHome)
        binDirectory = xiteHome + '/bin'
        pluginsDirectory = xiteHome + '/plugins'
        confDirectory = xiteHome + '/conf'
        libDirectory = xiteHome + '/lib'
    }
  
//    def String getSourceDirectory()
//    {
//        return this.normalize(sourceDirectory)
//    }
  
    def String toString()
    {
       return "xite home: ${xiteHome}, bin: ${binDirectory}, plugins: ${pluginsDirectory}, conf: ${confDirectory}, lib: ${libDirectory}, source: ${sourceDirectory}, destination: ${destinationDirectory}"
  }
    
    /**
     * Helper method for win usage
     */
    def String normalize(String path)
    {
        return Paths.normalizePath(path)
    }
    
    public static String normalizePath(String path)
    {
		if (!path) {
			return "";
		}
		println "path = ${path}"
		String n = path.replace(File.separatorChar, '/' as char);
		println "n = ${n}"
		String simplified = Files.simplifyPath(n);
		println "simpl = ${simplified}"
        return simplified
    }
}

