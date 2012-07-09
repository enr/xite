package xite;


import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import com.google.common.base.Charsets;

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
        Charset charsetInUse = (charset == null) ? Charsets.UTF_8 : charset;
        File outFile = outputFile;
        String contentToWrite = data;
        //String encoding = (charset == null) ? null : charset.name();
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
            //FileUtils.writeStringToFile(outputFile, contentToWrite, encoding);
            com.google.common.io.Files.write(contentToWrite, outputFile, charsetInUse);
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
