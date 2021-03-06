package com.github.enr.xite.plugins

import com.github.enr.xite.util.FilePaths;
import com.github.enr.xite.util.Strings;


/**
 * Copies html files, applying header and footer templates.
 *
 */
class HtmlPlugin extends XiteAbstractPlugin
{    
    private static final String SRC_BASE_DIR = 'html'
    
    PluginResult init() {}

    PluginResult apply() {
        def encoding = configuration.get("app.encoding")
        def htmlSourceDirectoryName = FilePaths.join(sourcePath, SRC_BASE_DIR)
        def resourcesDestinationDirectoryName = destinationPath
        reporter.debug('htmlSourceDirectoryName {}', htmlSourceDirectoryName)
        reporter.debug('resourcesDestinationDirectoryName {}', resourcesDestinationDirectoryName)
        def headerFileName = FilePaths.join(sourcePath, configuration.get('templates.directory'), configuration.get('templates.top'))
        def footerFileName = FilePaths.join(sourcePath, configuration.get('templates.directory'), configuration.get('templates.bottom'))

		def headerFile = new File(headerFileName)
        def header = (headerFile.exists() ? headerFile.getText(encoding) : "")
		def footerFile = new File(footerFileName)
        def footer = (footerFile.exists() ? footerFile.getText(encoding) : "")

        def htmlPath = FilePaths.normalizePath(htmlSourceDirectoryName)
        def htmlDirectory = new File(htmlPath)
        def htmlAbsolutePath = FilePaths.normalizePath(htmlDirectory.absolutePath)

        def ddf = new File(resourcesDestinationDirectoryName)
        def currentDestinationAbsolutePath = FilePaths.normalizePath(ddf.absolutePath)
		if (! htmlDirectory.exists()) {
			reporter.warn("html directory %s not found... skip html copying.", htmlPath)
			return
		}

        htmlDirectory.eachFileRecurse() { src ->
          def fap = FilePaths.normalizePath(src.absolutePath)
          def destinationFileName = fap.replace(htmlAbsolutePath, currentDestinationAbsolutePath)  
          reporter.debug("${src.getPath()} -> ${destinationFileName}")
          def destinationFile = new File(destinationFileName)
        
          if (src.isDirectory()) {
            if (!destinationFile.exists()) {
                destinationFile.mkdirs()
            }
          }
          
          if (src.isFile()) {
            def parentDirectory = new File(destinationFile.parent)
            if (!parentDirectory.exists()) {
              def success = parentDirectory.mkdirs()
              //parentDirectory.setWritable(true)
              assert success 
            }
            def content = src.text
            def finalContent = Strings.normalizeEol("${header}${content}${footer}")
            destinationFile.text = ''
            destinationFile << finalContent
          }
        }  
    }

    PluginResult cleanup() {}
}


