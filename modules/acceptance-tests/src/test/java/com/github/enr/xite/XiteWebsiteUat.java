package com.github.enr.xite;

import static org.testng.Assert.assertTrue;

import java.io.File;

import org.testng.annotations.Test;

import com.github.enr.xite.util.FilePaths;

/**
 * UAT against the main Xite website
 */
@Test(suiteName = "User acceptance tests for the actual Xite website")
public class XiteWebsiteUat extends BaseUat {

    private File websiteDirectory;
    private File destinationDirectory;

    @Test
    public void buildWebsite() {
        websiteDirectory = new File(xiteRoot, "website");
        assertTrue(websiteDirectory.exists());
        String websiteSourcePath = websiteDirectory.getAbsolutePath();

        destinationDirectory = new File("target/uat");
        String destinationPath = destinationDirectory.getAbsolutePath();
        // int result =
        runApplicationWithArgs(new String[] { "--stacktrace", "build", "-s", websiteSourcePath, "-d", destinationPath });
        // assertEquals(result, 0);
    }

    @Test(dependsOnMethods = { "buildWebsite" })
    public void htmlFilesAreGeneratedFromMarkdown() {
        File markdownDirectory = new File(websiteDirectory, "markdown");
        File[] markdownFiles = markdownDirectory.listFiles();
        for (File file : markdownFiles) {
            String fileName = file.getName();
            if (fileName.endsWith(".md")) {
                String outputPath = FilePaths.join(destinationDirectory.getAbsolutePath(), "xite", FilePaths.changeExtension(fileName, "html"));
                File outputFile = new File(outputPath);
                assertTrue(outputFile.exists(), outputPath);
            }
        }
    }

    @Test(dependsOnMethods = { "htmlFilesAreGeneratedFromMarkdown" })
    public void resourcesAreCopiedFromStandardDirectory() {
        File resourcesStandardDirectory = new File(websiteDirectory, "resources");
        File[] resources = resourcesStandardDirectory.listFiles();
        for (File file : resources) {
            String fileName = file.getName();
            String outputPath = FilePaths.join(destinationDirectory.getAbsolutePath(), "xite", fileName);
            File outputFile = new File(outputPath);
            assertTrue(outputFile.exists(), outputPath);
        }
    }

}