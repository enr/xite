package com.github.enr.xite.plugins

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;

import java.nio.charset.Charset

import xite.HtmlDirectoryLister;
import xite.Strings;
import xite.Files;
import xite.FilePaths;


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
        def headerFileName = "${sourcePath}/${configuration.get('templates.directory')}/${configuration.get('templates.top')}"
		if (configuration.get('code.top')) {
			headerFileName = "${sourcePath}/"+configuration.get('code.top')
		}
        def footerFileName = "${sourcePath}/${configuration.get('templates.directory')}/${configuration.get('templates.bottom')}"
		if (configuration.get('code.bottom')) {
			footerFileName = "${sourcePath}/"+configuration.get('code.bottom')
		}
        def baseContext = configuration.get('code.baseContext')
        def excludedDirs = configuration.get("code.sources.excludes")
        def encoding = configuration.get("app.encoding")
		def headerFile = new File(headerFileName)
        def header = (headerFile.exists() ? headerFile.getText(encoding) : "")
		def footerFile = new File(footerFileName)
        def footer = (footerFile.exists() ? footerFile.getText(encoding) : "")
        def codeSourceDirectory = "${sourcePath}/"+configuration.get('code.source')
		reporter.out("codeSourceDirectory = %s", codeSourceDirectory)
        def codeDestinationDirectory = "${destinationPath}/"+configuration.get('code.destination')
		reporter.out("codeDestinationDirectory = %s", codeDestinationDirectory)
        // a map of resources directory -> sub directory of destination
        def codeSourceDirectories = [:]
        codeSourceDirectories.put(codeSourceDirectory, '')
        //logger.debug('configuration.code.sources.additionals: {}', configuration.get('code.sources.additionals'))
        for (additional in configuration.get('code.sources.additionals')) {
            codeSourceDirectories.put(sourcePath + '/' + additional.key, additional.value)
        }
        /*
        logger.debug("headerFileName ${headerFileName}")
        logger.debug("footerFileName ${footerFileName}")
        logger.debug("codeSourceDirectories ${codeSourceDirectories}")
        logger.debug("codeDestinationDirectory ${codeDestinationDirectory}")
        */
        for (codeDir in codeSourceDirectories) {
          def cs = FilePaths.normalizePath(codeDir.key)
          def csf = new File(cs)
          def currentCodeAbsolutePath = FilePaths.normalizePath(csf.absolutePath)
          def dst = codeDir.value
          def dd = (dst.trim() != '') ? "${codeDestinationDirectory}/${dst}" : codeDestinationDirectory
          def ddf = new File(dd)
          def currentDestinationAbsolutePath = FilePaths.normalizePath(ddf.absolutePath)
          //logger.debug("processing dir ${currentCodeAbsolutePath}, target dir: ${currentDestinationAbsolutePath}")
        
          if (!csf.exists()) {
              //logger.debug("source directory ${currentCodeAbsolutePath} not found")
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
            reporter.debug(" F    ${fap}")
            reporter.debug(" S    ${currentCodeAbsolutePath}")
            reporter.debug(" D    ${currentDestinationAbsolutePath}")
            def extension = com.google.common.io.Files.getFileExtension(fap.toString()); // FilePaths.extension(fap.toString())
            // what if file has no extension??
            def lang = codeLang(extension)
            reporter.debug("lang : ${lang}")
            // file extension is taken as lang
            def destinationFileNameNoHtmlExtension = fap.replace(currentCodeAbsolutePath, currentDestinationAbsolutePath)
            reporter.debug("         replace : ${currentCodeAbsolutePath}")
            def fileTitle = fap.replace(currentCodeAbsolutePath, "")
            reporter.debug("         fileTitle : ${fileTitle}")
            def destinationFileName = "${destinationFileNameNoHtmlExtension}.html"
            reporter.debug("destinationFileName : ${destinationFileName}")
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
			  Files.write(destinationFile, finalContent, Charset.forName(encoding));
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


