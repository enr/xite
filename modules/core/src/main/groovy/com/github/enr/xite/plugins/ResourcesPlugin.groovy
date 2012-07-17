package com.github.enr.xite.plugins

import xite.FilePaths

class ResourcesPlugin extends XiteAbstractPlugin
{
    PluginResult init() {}

    PluginResult apply() {

def processResources = configuration.get('resources.filter.enabled')
def resourcesFiltersFile = configuration.get('resources.filter.properties')

def prefix =  configuration.get('resources.filter.prefix')
def suffix =  configuration.get('resources.filter.suffix')

def substitutionsFilePath = "${sourcePath}/${resourcesFiltersFile}"

File substitutionsFile = new File(substitutionsFilePath)

Properties subs = new Properties();
if (substitutionsFile.exists()) {
  FileInputStream inp = new FileInputStream(substitutionsFilePath);
  subs.load(inp);
  inp.close();
}

reporter.debug('sourcePath {}', sourcePath)
def resourcesSourceDirectoryName = sourcePath + '/' + configuration.get('resources.directory')
def resourcesDestinationDirectoryName = destinationPath

def excludedFilenameSuffix = configuration.get('resources.excludedFilenameSuffix')

// a map of resources directory -> sub directory of destination
def resourcesDirectories = [:]
resourcesDirectories.put("${resourcesSourceDirectoryName}", '')
reporter.debug('configuration.resources.sources.additionals: {}', configuration.get('resources.sources.additionals'))
for (a in configuration.get('resources.sources.additionals')) {
    resourcesDirectories.put(sourcePath + '/' + a.key, a.value)
}

reporter.info('resources directories: {}', resourcesDirectories);


reporter.debug('filter resources? {}', processResources)
reporter.debug('prefix {}', prefix)
reporter.debug('suffix {}', suffix)
reporter.debug('subs {}', subs)
reporter.debug('resourcesSourceDirectoryName {}', resourcesSourceDirectoryName)
reporter.debug('resourcesDestinationDirectoryName {}', resourcesDestinationDirectoryName)
reporter.debug('excludedFilenameSuffix {}', excludedFilenameSuffix)

for (resDir in resourcesDirectories)
{
  def rdn = FilePaths.normalizePath(resDir.key)
  def rdf = new File(rdn)
  def currentResourcesAbsolutePath = FilePaths.normalizePath(rdf.absolutePath)
  def dst = resDir.value
  def dd = (dst.trim() != '') ? "${resourcesDestinationDirectoryName}/${dst}" : resourcesDestinationDirectoryName
  def ddf = new File(dd)
  def currentDestinationAbsolutePath = FilePaths.normalizePath(ddf.absolutePath)
  reporter.info("processing resource ${currentResourcesAbsolutePath} target path: ${currentDestinationAbsolutePath}")
  if (!rdf.exists()) {
      reporter.warn("resource ${currentResourcesAbsolutePath} not found")
      continue
  }
  if (! rdf.isDirectory()) {
      ddf.text = rdf.text
      continue
  }
  rdf.eachFileRecurse() { src ->
    def fap = FilePaths.normalizePath(src.absolutePath)
    for (efs in excludedFilenameSuffix) {
        if (fap.endsWith(efs)) {
            reporter.info("skipping ${fap} (${efs})")
            return
        }
    }
    reporter.debug("processing ${fap}")
    reporter.debug("replace: ${currentResourcesAbsolutePath} . ${currentDestinationAbsolutePath}")
    def destinationFileName = fap.replace(currentResourcesAbsolutePath, currentDestinationAbsolutePath)  
    reporter.debug("${src.getPath()} -> ${destinationFileName}")
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
        reporter.info("directory ${parentDirectory} NO exists. creating...")
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


