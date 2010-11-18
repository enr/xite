package xite.plugin.html

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xite.Strings;

import xite.XiteAbstractPlugin
import xite.api.PluginResult

/**
 * Copies html files, applying header and footer templates.
 *
 */
class HtmlPlugin extends XiteAbstractPlugin
{
    PluginResult init() {}
    PluginResult apply() {

def logger = LoggerFactory.getLogger('xite.html');

def htmlSourceDirectoryName = paths.sourceDirectory + '/html'
def resourcesDestinationDirectoryName = paths.destinationDirectory

logger.debug('htmlSourceDirectoryName {}', htmlSourceDirectoryName)
logger.debug('resourcesDestinationDirectoryName {}', resourcesDestinationDirectoryName)


def headerFileName = "${paths.sourceDirectory}/${configuration.templates.directory}/${configuration.templates.top}"
def footerFileName = "${paths.sourceDirectory}/${configuration.templates.directory}/${configuration.templates.bottom}"

logger.debug("headerFileName ${headerFileName}")
logger.debug("footerFileName ${footerFileName}")

def header = new File(headerFileName).text
def footer = new File(footerFileName).text

def htmlPath = paths.normalize(htmlSourceDirectoryName)
def htmlDirectory = new File(htmlPath)
def htmlAbsolutePath = paths.normalize(htmlDirectory.absolutePath)

def ddf = new File(resourcesDestinationDirectoryName)
def currentDestinationAbsolutePath = paths.normalize(ddf.absolutePath)
logger.warn("\nprocessing dir ${htmlAbsolutePath}\ntarget dir: ${currentDestinationAbsolutePath}")
if (!htmlDirectory.exists()) {
  logger.warn("source directory ${htmlAbsolutePath} not found")
}
  
htmlDirectory.eachFileRecurse() { src ->
  def fap = paths.normalize(src.absolutePath)

  logger.debug("processing ${fap}")
  logger.debug("replace: ${htmlAbsolutePath} . ${currentDestinationAbsolutePath}")

  def destinationFileName = fap.replace(htmlAbsolutePath, currentDestinationAbsolutePath)  
  logger.debug("${src.getPath()} -> ${destinationFileName}")
  def destinationFile = new File(destinationFileName)

  if (src.isDirectory()) {
    if (!destinationFile.exists()) {
        destinationFile.mkdirs()
    }
  }
  
  if (src.isFile()) {
    def parentDirectory = new File(destinationFile.parent)
    if (!parentDirectory.exists()) {
      logger.info("directory ${parentDirectory} NO exists. creating...")
      def success = parentDirectory.mkdirs()
      //parentDirectory.setWritable(true)
      assert success 
    }
    def content = src.text
    def finalContent = Strings.normalizeEol("${header}${content}${footer}")
    //logger.warn(finalContent)
    destinationFile.text = ''
    destinationFile << finalContent
  }
}  

    }
    PluginResult cleanup() {}
}


