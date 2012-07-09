package com.github.enr.xite;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;

import org.testng.annotations.Test;


/**
* UAT against the main Xite website
*/
@Test(suiteName = "User acceptance tests")
public class XiteWebsiteUat extends BaseUat {

    public void htmlFilesAreGeneratedFromMarkdown() {
    	File websiteDirectory = new File(xiteRoot, "website");
    	assertTrue(websiteDirectory.exists());
    	String websiteSourcePath = websiteDirectory.getAbsolutePath();
    	
    	File destinationDirectory = new File("target/uat");
    	String destinationPath = destinationDirectory.getAbsolutePath();
        int result = runApplicationWithArgs(new String[] { "-s", websiteSourcePath, "-d", destinationPath});
        assertEquals(result, 0);
        
        File markdownDirectory = new File(websiteDirectory, "markdown");
        File[] markdownFiles = markdownDirectory.listFiles();
        for (File file : markdownFiles) {
    		String fileName = file.getName();
        	if (fileName.endsWith(".md")) {
				String outputPath = destinationPath + "/" + expectedOutputFilePath(fileName);
        		File outputFile = new File(outputPath);
        		assertTrue(outputFile.exists(), fileName);
        	}
		}
    }
    
    private String expectedOutputFilePath(String fileName) {
    	String rawFileName = fileName.substring(0, fileName.lastIndexOf("."));
    	return "xite/"+rawFileName+".html";
    }

}