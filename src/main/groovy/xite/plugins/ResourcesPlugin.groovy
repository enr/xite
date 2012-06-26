package xite.plugins

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xite.XiteAbstractPlugin
import xite.api.PluginResult

class ResourcesPlugin extends XiteAbstractPlugin
{
    PluginResult init() {}

    PluginResult apply() {

def logger = LoggerFactory.getLogger('xite.resources');

def processResources = configuration.resources.filter.enabled
def resourcesFiltersFile = configuration.resources.filter.properties

def prefix =  configuration.resources.filter.prefix
def suffix =  configuration.resources.filter.suffix

def substitutionsFilePath = "${paths.sourceDirectory}/${resourcesFiltersFile}"

File substitutionsFile = new File(substitutionsFilePath)

Properties subs = new Properties();
if (substitutionsFile.exists()) {
  FileInputStream inp = new FileInputStream(substitutionsFilePath);
  subs.load(inp);
  inp.close();
}

def resourcesSourceDirectoryName = paths.sourceDirectory + '/' + configuration.resources.directory
def resourcesDestinationDirectoryName = paths.destinationDirectory

def excludedFilenameSuffix = configuration.resources.excludedFilenameSuffix

// a map of resources directory -> sub directory of destination
def resourcesDirectories = [:]
resourcesDirectories.put("${resourcesSourceDirectoryName}", '')
logger.debug('configuration.resources.sources.additionals: {}', configuration.resources.sources.additionals)
for (a in configuration.resources.sources.additionals) {
    resourcesDirectories.put(a.key, a.value)
}

logger.info('resources directories: {}', resourcesDirectories);


logger.debug('filter resources? {}', processResources)
logger.debug('prefix {}', prefix)
logger.debug('suffix {}', suffix)
logger.debug('subs {}', subs)
logger.debug('resourcesSourceDirectoryName {}', resourcesSourceDirectoryName)
logger.debug('resourcesDestinationDirectoryName {}', resourcesDestinationDirectoryName)
logger.debug('excludedFilenameSuffix {}', excludedFilenameSuffix)

for (resDir in resourcesDirectories)
{
  def rdn = paths.normalize(resDir.key)
  def rdf = new File(rdn)
  def currentResourcesAbsolutePath = paths.normalize(rdf.absolutePath)
  def dst = resDir.value
  def dd = (dst.trim() != '') ? "${resourcesDestinationDirectoryName}/${dst}" : resourcesDestinationDirectoryName
  def ddf = new File(dd)
  def currentDestinationAbsolutePath = paths.normalize(ddf.absolutePath)
  logger.info("processing resource ${currentResourcesAbsolutePath} target path: ${currentDestinationAbsolutePath}")
  if (!rdf.exists()) {
      logger.warn("resource ${currentResourcesAbsolutePath} not found")
      continue
  }
  if (! rdf.isDirectory()) {
      ddf.text = rdf.text
      continue
  }
  rdf.eachFileRecurse() { src ->
    def fap = paths.normalize(src.absolutePath)
    for (efs in excludedFilenameSuffix) {
        if (fap.endsWith(efs)) {
            logger.info("skipping ${fap} (${efs})")
            return
        }
    }
    logger.debug("processing ${fap}")
    logger.debug("replace: ${currentResourcesAbsolutePath} . ${currentDestinationAbsolutePath}")
    def destinationFileName = fap.replace(currentResourcesAbsolutePath, currentDestinationAbsolutePath)  
    logger.debug("${src.getPath()} -> ${destinationFileName}")
    def destinationFile = new File(destinationFileName)
    if (destinationFile.exists()) { destinationFile.delete() }

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
      if (!processResources) {
        destinationFile << src.asWritable()
      } else {
        destinationFile.withWriter { f ->
          src.eachLine { line ->
            subs.each() { key, value ->
              def placeholder = "${prefix}${key}${suffix}"
              line = line.replace(placeholder, value)
            }
            f.writeLine( line )
          }
        }
      }
    }
  }  
}

    return new PluginResult()
    }
    
    PluginResult cleanup() {}
}


