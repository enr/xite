package com.github.enr.xite.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides utility methods for working with directories.
 * 
 * @author javapractices.com
 * @author Alex Wong
 */
public final class Directories
{
    /**
     * Only static methods in this class
     */
    private Directories()
    {
    }

    public static List<File> list(File directory, FilenameFilter filter, boolean recurse)
    {
        List<File> files = new ArrayList<File>();
        File[] entries = directory.listFiles();
        for (File entry : entries)
        {
            if (filter == null || filter.accept(directory, entry.getName()))
            {
                files.add(entry);
            }
            if (recurse && entry.isDirectory())
            {
                files.addAll(list(entry, filter, recurse));
            }
        }
        return files;
    }

    public static List<File> list(File directory, FileFilter filter, boolean recurse)
    {
        List<File> files = new ArrayList<File>();
        File[] entries = directory.listFiles();
        for (File entry : entries)
        {
            if (filter == null || filter.accept(entry))
            {
                files.add(entry);
            }
            if (recurse && entry.isDirectory())
            {
                files.addAll(list(entry, filter, recurse));
            }
        }
        return files;
    }
    
    public static boolean isEmpty(File directory)
    {
        if (! directory.isDirectory()) return true;
        return (directory.listFiles().length < 1);
    }

}
