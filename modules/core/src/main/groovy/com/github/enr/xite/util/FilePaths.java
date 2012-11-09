package com.github.enr.xite.util;

import java.io.File;
import java.net.URI;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.io.Files;

public class FilePaths {

    private FilePaths() {
    }

    /*
     * trasforms relative paths in absolute, ie. ../tests/docs is transformed in
     * D:\path\to\tests\docs
     * 
     * @param path
     * 
     * @return the normalized, absolute path
     * 
     * public static String _absoluteNormalized(String path) { File file = new
     * File(path); return absoluteNormalized(file); }
     */

    /**
     * Resolves the absolute path to the given file. Path will be normalized (eg
     * with / as file separator).
     * 
     * @param file
     * @return
     */
    public static String absoluteNormalized(File file) {
        URI ud = file.toURI();
        URI un = ud.normalize();
        File f = new File(un);
        String absolutePath = f.getAbsolutePath();
        return absolutePath.replace(File.separatorChar, '/');
    }

    public static String normalizePath(String path) {
        if (path == null) {
            return "";
        }
        String n = path.replace(File.separatorChar, '/');
        String simplified = Files.simplifyPath(n);
        return simplified;
    }

    public static String join(String... pathTokens) {
        Joiner joiner = Joiner.on("/").skipNulls();
        return joiner.join(pathTokens);
    }
    
    /*
     * modify file extension and returns the complete filename
     */
    public static String changeExtension(String fileName, String ext) {
        String fn = Preconditions.checkNotNull(fileName);
        String rawFileName = fn.substring(0, fn.lastIndexOf("."));
        return rawFileName + "." + ext;
    }

    /*
     * public static String _absolute(File file) { URI ud = file.toURI(); URI un
     * = ud.normalize(); File f = new File(un); String absolutePath =
     * f.getAbsolutePath(); return absolutePath; }
     */

    /**
     * Replaces all backslashes with slash char. Throws NPE if the original path
     * is null.
     * 
     * @param original
     *            : the path to normalize.
     * 
     * 
     *            public static String _normalized(String original) { return
     *            original.replace(File.separatorChar, '/'); }
     */

    /**
     * Resolve the extension for the given filename. Throws NPE if filename is
     * null.
     * 
     * @param filename
     * @return the file extension (without dot) if any, or empty string if
     *         filename doesn't contain any dot.
     * 
     *         public static String _extension(String filename) { String
     *         extension = ""; String fn = filename.trim(); int dotPos =
     *         fn.lastIndexOf("."); if (dotPos == -1) { return ""; } extension =
     *         fn.substring(dotPos + 1); return extension; }
     */

    /*
     * public static String _stripExtension(String filename) { int
     * extensionIndex = filename.lastIndexOf("."); if (extensionIndex == -1) {
     * return filename; } return filename.substring(0, extensionIndex); }
     */

}
