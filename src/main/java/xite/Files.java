package xite;

import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Provides utility methods for working with files.
 * 
 * @author enr
 * 
 */
public class Files
{
    /**
     * Only static methods in this class
     */
    private Files()
    {
    }

    /**
     * Resolve the extension for the given filename. Throws NPE if filename is
     * null.
     * 
     * @param filename
     * @return the file extension (without dot) if any, or empty string if filename doesn't contain any dot.
     */
    public static String extension(String filename)
    {
        String extension = "";
        String fn = filename.trim();
        int dotPos = fn.lastIndexOf(".");
        if (dotPos == -1)
        {
            return "";
        }
        extension = fn.substring(dotPos + 1);
        return extension;
    }

    /**
     * Replaces all backslashes with slash char. Throws NPE if the original path
     * is null.
     * 
     * @param original :
     *            the path to normalize.
     * @return
     */
    public static String normalizedPath(String original)
    {
        return original.replace(File.separatorChar, '/');
    }
    
    /**
     * writes using platform default encoding
     * 
     * @param outputFile
     * @param data
     */
    public static void write(File outputFile, String data)
    {
        Files.write(outputFile, data, null);
    }

    /*
     * Scrive content su file outputFile: se file non esiste lo crea, se esiste
     * lo sovrascrive.
     */
    public static void write(File outputFile, String data, Charset charset)
    {
        if (outputFile == null) {
            throw new RuntimeException("xite.Files.write(): outputFile cannot be null.");
        }
        if (data == null) {
            throw new RuntimeException("xite.Files.write(): data cannot be null.");
        }
        File outFile = outputFile;
        String contentToWrite = data;
        String encoding = (charset == null) ? null : charset.name();
        // creates the needed directory structure
        File parent = outFile.getParentFile();
        if (!parent.exists())
        {
            parent.mkdirs();
        }
        // overwrite the file, if it exists
        if (outFile.exists())
        {
            outFile.delete();
        }
        try
        {
            outFile.createNewFile();
            FileUtils.writeStringToFile(outputFile, contentToWrite, encoding);
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
