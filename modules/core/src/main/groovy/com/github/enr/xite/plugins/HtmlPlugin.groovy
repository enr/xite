package com.github.enr.xite.plugins

import xite.FilePaths
import xite.Strings


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
        reporter.out('htmlSourceDirectoryName {}', htmlSourceDirectoryName)
        reporter.debug('resourcesDestinationDirectoryName {}', resourcesDestinationDirectoryName)
        def headerFileName = FilePaths.join(sourcePath, configuration.get('templates.directory'), configuration.get('templates.top'))
        def footerFileName = FilePaths.join(sourcePath, configuration.get('templates.directory'), configuration.get('templates.bottom'))
        reporter.debug("headerFileName ${headerFileName}")
        reporter.debug("footerFileName ${footerFileName}")

		def headerFile = new File(headerFileName)
        def header = (headerFile.exists() ? headerFile.getText(encoding) : "")
		def footerFile = new File(footerFileName)
        def footer = (footerFile.exists() ? footerFile.getText(encoding) : "")

        def htmlPath = FilePaths.normalizePath(htmlSourceDirectoryName)
        def htmlDirectory = new File(htmlPath)
        def htmlAbsolutePath = FilePaths.normalizePath(htmlDirectory.absolutePath)

        def ddf = new File(resourcesDestinationDirectoryName)
        def currentDestinationAbsolutePath = FilePaths.normalizePath(ddf.absolutePath)
        reporter.debug("processing dir ${htmlAbsolutePath}")
        reporter.debug("target dir: ${currentDestinationAbsolutePath}")
		if (! htmlDirectory.exists()) {
			reporter.out("html directory %s not found... skip html copying.", htmlPath)
			return
		}

        htmlDirectory.eachFileRecurse() { src ->
          def fap = FilePaths.normalizePath(src.absolutePath)
        
          reporter.debug("processing ${fap}")
          reporter.debug("replace: ${htmlAbsolutePath} . ${currentDestinationAbsolutePath}")
        
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
              reporter.info("directory ${parentDirectory} NO exists. creating...")
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


