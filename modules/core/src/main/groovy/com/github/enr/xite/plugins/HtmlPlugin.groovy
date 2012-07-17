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
        def htmlSourceDirectoryName = sourcePath + '/' + SRC_BASE_DIR
        def resourcesDestinationDirectoryName = destinationPath
        reporter.debug('htmlSourceDirectoryName {}', htmlSourceDirectoryName)
        reporter.debug('resourcesDestinationDirectoryName {}', resourcesDestinationDirectoryName)
        def headerFileName = "${sourcePath}/${configuration.get('templates.directory')}/${configuration.get('templates.top')}"
        def footerFileName = "${sourcePath}/${configuration.get('templates.directory')}/${configuration.get('templates.bottom')}"
        reporter.debug("headerFileName ${headerFileName}")
        reporter.debug("footerFileName ${footerFileName}")

        def header = new File(headerFileName).text
        def footer = new File(footerFileName).text

        def htmlPath = FilePaths.normalizePath(htmlSourceDirectoryName)
        def htmlDirectory = new File(htmlPath)
        def htmlAbsolutePath = FilePaths.normalizePath(htmlDirectory.absolutePath)

        def ddf = new File(resourcesDestinationDirectoryName)
        def currentDestinationAbsolutePath = FilePaths.normalizePath(ddf.absolutePath)
        reporter.debug("processing dir ${htmlAbsolutePath}")
        reporter.debug("target dir: ${currentDestinationAbsolutePath}")
        if (!htmlDirectory.exists()) {
            reporter.warn("source directory ${htmlAbsolutePath} not found")
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


