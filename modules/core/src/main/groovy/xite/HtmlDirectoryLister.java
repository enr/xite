package xite;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtmlDirectoryLister
{

    static private Logger logger;

    @SuppressWarnings("static-access")
    protected Logger log()
    {
        if (this.logger == null)
            this.logger = LoggerFactory.getLogger(this.getClass());
        return this.logger;
    }
    
    /**
     * Base directory to list
     */
    private final File directory;
    
    /**
     * Base context for links
     */
    private final String context;
    
    /**
     * A string appended before the actual listing
     */
    private final String header;
    
    /**
     * A string appended after the actual listing
     */
    private final String footer;
    
    /**
     * Html element id for the div containing the listing
     */
    private final String divId;
    
    /**
     * Css class used for directories names in the listing
     */
    private final String directoryCssClass;
    
    /**
     * Css class used for files names in the listing
     */
    private final String fileCssClass;
    
    /**
     * If listing should contains the title string for an empty directory
     */
    private final boolean listEmptyDirs;
    
    /**
     * Content of the directory listing page
     */
    private StringBuilder pageContent;
    
    /**
     * Normalized path for base directory
     */
    private String basePath;

    public static class Builder
    {
        // Required parameters
        private final File directory;
        // Optional parameters - initialized to default values
        private String context = "";
        private String header = "";
        private String footer = "";
        private String divId = "";
        private String directoryCssClass = "";
        private String fileCssClass = "";
        private boolean listEmptyDirs = true;

        public Builder(File directory)
        {
            this.directory = directory;
        }

        public Builder context(String val)
        {
            context = val;
            return this;
        }

        public Builder header(String val)
        {
            header = val;
            return this;
        }

        public Builder footer(String val)
        {
            footer = val;
            return this;
        }

        public Builder divId(String val)
        {
            divId = val;
            return this;
        }
        
        public Builder directoryCssClass(String val)
        {
            directoryCssClass = val;
            return this;
        }

        public Builder fileCssClass(String val)
        {
            fileCssClass = val;
            return this;
        }
        
        public Builder listEmptyDirs(boolean val)
        {
            listEmptyDirs = val;
            return this;
        }

        public HtmlDirectoryLister build()
        {
            return new HtmlDirectoryLister(this);
        }
    }

    private HtmlDirectoryLister(Builder builder)
    {
        directory = builder.directory;
        context = builder.context;
        header = builder.header;
        footer = builder.footer;
        divId = builder.divId;
        directoryCssClass = builder.directoryCssClass;
        fileCssClass = builder.fileCssClass;
        listEmptyDirs = builder.listEmptyDirs;
    }

    public void write()
    {
        if (!directory.exists()) directory.mkdirs();
        File page = new File(directory, "index.html");
        pageContent = new StringBuilder(Strings.normalizeEol(header));
        pageContent.append("\n<div id=");
        pageContent.append('"');
        pageContent.append(divId);
        pageContent.append('"');
        pageContent.append(">\n");
        
        basePath = FilePaths.absoluteNormalized(directory);
        
        FileTraverser ft = new FileTraverser() {
            @Override
            public void onDirectory(final File f)
            {
                if ((! Directories.isEmpty(f)) || (listEmptyDirs)) {
                    log().debug("D f : {}", f);
                    String relativePath = FilePaths.absoluteNormalized(f).replaceFirst(basePath, "");
                    log().debug("D relativePath : {}", relativePath);
                    String descriptivePath = relativePath.replaceFirst("/", "");
                    descriptivePath = descriptivePath.replace("/", " &gt; ");
                    log().debug("D descriptivePath: {}", descriptivePath);
                    if ((descriptivePath == null) || ("".equals(descriptivePath.trim()))) return;
                    pageContent.append("\n<p/><span class=");
                    pageContent.append('"');
                    pageContent.append(directoryCssClass);
                    pageContent.append('"');
                    pageContent.append('>');
                    pageContent.append(descriptivePath);
                    pageContent.append("</span>");
                } else {
                    log().info("skipping empty directory '{}'", f.getAbsolutePath());
                }
            }
            @Override
            public void onFile(final File f)
            {
                log().debug("F  : {}", f);
                String relativePath = FilePaths.absoluteNormalized(f).replaceFirst(basePath, "");
                log().debug("F relative path : {}", relativePath);
                String descriptivePath = relativePath.replaceFirst("/", "");
                descriptivePath = descriptivePath.replace("/", " &gt; ");
                log().debug("F descriptivePath : {}", descriptivePath);
                if ((descriptivePath == null) || ("".equals(descriptivePath.trim()))) return;
                pageContent.append("\n<p/><a href=");
                pageContent.append('"');
                pageContent.append(context);
                pageContent.append(relativePath);
                pageContent.append('"');
                pageContent.append(" class=");
                pageContent.append('"');
                pageContent.append(fileCssClass);
                pageContent.append('"');
                pageContent.append('>');
                pageContent.append(descriptivePath);
                pageContent.append("</a>");
            }
        };
        try
        {
            ft.traverse(directory);
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        pageContent.append("\n</div>\n");
        pageContent.append(Strings.normalizeEol(footer));
        String c = pageContent.toString();
        if (page.exists()) {
            page.delete();
        }
        Files.write(page, c);
    }

}
