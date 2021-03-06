package com.github.enr.xite.plugins

import java.io.IOException
import java.nio.charset.Charset

import com.github.enr.xite.util.Configurations
import com.github.enr.xite.util.FilePaths
import com.github.enr.xite.util.HtmlDirectoryLister
import com.github.enr.xite.util.Strings
import com.google.common.base.Throwables
import com.google.common.io.Files



/**
 * Generates copy of code files in HTML format, keeping the sources directory structure.
 *
 */
class CodePlugin extends XiteAbstractPlugin
{
    
    PluginResult init() {}

    PluginResult apply() {
        def excludedFilenameSuffix = configuration.get('code.excludedFilenameSuffix')
        // todo if code header/footer not in config, use default header/footer
        def headerFileName = FilePaths.join(sourcePath, configuration.get('templates.directory'), configuration.get('templates.top'))
		if (configuration.get('code.top') != null) {
			headerFileName = FilePaths.join(sourcePath, configuration.get('code.top'))
		}
        def footerFileName = FilePaths.join(sourcePath, configuration.get('templates.directory'), configuration.get('templates.bottom'))
		if (configuration.get('code.bottom') != null) {
			footerFileName = FilePaths.join(sourcePath, configuration.get('code.bottom'))
		}
        def baseContext = configuration.get('code.baseContext')
        def excludedDirs = configuration.get("code.sources.excludes")
        def encoding = configuration.get("app.encoding")
		def headerFile = new File(headerFileName)
        def header = ((headerFile.exists() && headerFile.isFile()) ? headerFile.getText(encoding) : "")
		def footerFile = new File(footerFileName)
        def footer = ((footerFile.exists() && footerFile.isFile()) ? footerFile.getText(encoding) : "")
        def codeSourceDirectory = FilePaths.join(sourcePath, configuration.get('code.source'))
		reporter.debug("codeSourceDirectory = %s", codeSourceDirectory)
        def codeDestinationDirectory = FilePaths.join(destinationPath, configuration.get('code.destination'))
		reporter.debug("codeDestinationDirectory = %s", codeDestinationDirectory)
        // a map of resources directory -> sub directory of destination
		
		def codeSourceDirectories = Configurations.getAdditionals(configuration.getBulk('code.sources.additionals'), sourcePath)
        codeSourceDirectories.put(codeSourceDirectory, '')
        for (codeDir in codeSourceDirectories) {
          def cs = FilePaths.normalizePath(codeDir.key)
          def csf = new File(cs)
          def currentCodeAbsolutePath = FilePaths.normalizePath(csf.absolutePath)
          def dst = codeDir.value
          def dd = (dst.trim() != '') ? FilePaths.join(codeDestinationDirectory, dst) : codeDestinationDirectory
          def ddf = new File(dd)
          def currentDestinationAbsolutePath = FilePaths.normalizePath(ddf.absolutePath)
          reporter.debug("processing dir %s, target dir: %s", currentCodeAbsolutePath, currentDestinationAbsolutePath)
        
          if (!csf.exists()) {
              reporter.warn("source directory %s not found", currentCodeAbsolutePath)
              continue
          }
		  
		  File indexFile = new File(ddf, "index.html")
		  if (indexFile.exists()) {
			  indexFile.delete()
		  }
          
          csf.eachFileRecurse() { src ->
            def fap = FilePaths.normalizePath(src.absolutePath)
			//logger.info("code ${fap}")
            for (efs in excludedFilenameSuffix) {
                if (fap.endsWith(efs)) {
                    //logger.warn("skipping ${fap} (${efs})")
                    return
                }
            }
            // TODO: for rd in excluded dirs...
            // xite sources dir is skipped
            for (excluded in excludedDirs) {
              def rd = FilePaths.normalizePath(new File(excluded).getAbsolutePath())
              if (fap.startsWith(rd)) {
                reporter.warn("skipping file ${fap} in excluded dir")
                return
              }
            }

            def extension = Files.getFileExtension(fap.toString()); // FilePaths.extension(fap.toString())
            // what if file has no extension??
            def lang = codeLang(extension)

            // file extension is taken as lang
            def destinationFileNameNoHtmlExtension = fap.replace(currentCodeAbsolutePath, currentDestinationAbsolutePath)
            def fileTitle = fap.replace(currentCodeAbsolutePath, "")
            def destinationFileName = "${destinationFileNameNoHtmlExtension}.html"
            def destinationFile = new File(destinationFileName)
            if (destinationFile.exists()) { destinationFile.delete() }
            def destinationFileNoHtmlExtension = new File(destinationFileNameNoHtmlExtension)
          
            if (src.isDirectory()) {
              if (!destinationFileNoHtmlExtension.exists()) {
                  destinationFileNoHtmlExtension.mkdirs()
              }
            }
            
            if (src.isFile()) {
              def parentDirectory = new File(destinationFile.parent)
              if (!parentDirectory.exists()) {
                def success = parentDirectory.mkdirs()
                assert success 
              }
              String codeString = src.getText(encoding);
              def content = String.format(configuration.get('code.template'), lang, codeString)
              def headerWithHeading = "${header}<p/><h3>${fileTitle}</h3><p/>"
              String finalContent = Strings.normalizeEol("${headerWithHeading}${content}${footer}");
			  //Files.write(destinationFile, finalContent, Charset.forName(encoding));
			  try {
				  Files.write(finalContent, destinationFile, Charset.forName(encoding));
			  } catch (IOException e) {
				  Throwables.propagate(e);
			  }
            }
          }
        }

        HtmlDirectoryLister lister = new HtmlDirectoryLister.Builder(new File(codeDestinationDirectory))
                            .context("${baseContext}/code")
                            .header(header)
                            .footer(footer)
                            .divId('dir-listing')
                            .directoryCssClass('dir-listing-d')
                            .fileCssClass('dir-listing-f')
                            .build();

        lister.write();
    }
   
	/**
	 * Resolves the language from the file extension, returning a lang understood from syntax highlighting js library
	 *
	 */
	private String codeLang(String extension)
	{
	    if (extension == null) return ''
	    if (extension == '') return ''
	    if (extension == 'sh') return 'shell'
	    if (extension == 'py') return 'python'
	    return extension
	}

}


