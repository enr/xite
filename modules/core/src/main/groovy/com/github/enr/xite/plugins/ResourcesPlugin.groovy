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

reporter.out('resources.... sourcePath %s', sourcePath)
def resourcesSourceDirectoryName = sourcePath + '/' + configuration.get('resources.directory')
def resourcesDestinationDirectoryName = destinationPath

def excludedFilenameSuffix = configuration.get('resources.excludedFilenameSuffix')

// a map of resources directory -> sub directory of destination
def resourcesDirectories = [:]
resourcesDirectories.put("${resourcesSourceDirectoryName}", '')
reporter.out('configuration.resources.sources.additionals: %s', configuration.get('resources.sources.additionals'))
def adds = [:]
for (a in configuration.getBulk('resources.sources.additionals')) {
	reporter.debug("additional %s %s", a, a.getClass().getName())
    //resourcesDirectories.put(sourcePath + '/' + a.source, a.destination)
	reporter.debug('[A] _%s_ => %s', a.key.toString(), a.value);
	def tokens = a.key.toString().split("\\.");
	println tokens
	//reporter.out('tokens => %s', tokens);
	if (tokens.size() > 0) {
		def additionalId = tokens[0]
		def additionalRole = tokens[1]
		reporter.debug('id: %s , role: %s', additionalId, additionalRole);
		if (!adds[additionalId]) {
			adds[additionalId] = [:]
		}
		adds[additionalId][additionalRole] = a.value
	}
}

for (ad in adds) {
	reporter.debug('id %s = %s', ad.key, ad.value);
	reporter.debug('     %s => %s', ad.value.source, ad.value.destination);
	resourcesDirectories.put(sourcePath + '/' + ad.value.source, ad.value.destination)
}

reporter.debug('resources directories: %s', resourcesDirectories);


reporter.debug('filter resources? {}', processResources)
reporter.debug('prefix {}', prefix)
reporter.debug('suffix {}', suffix)
reporter.debug('subs {}', subs)
reporter.debug('resourcesSourceDirectoryName {}', resourcesSourceDirectoryName)
reporter.debug('resourcesDestinationDirectoryName {}', resourcesDestinationDirectoryName)
reporter.debug('excludedFilenameSuffix {}', excludedFilenameSuffix)

for (resDir in resourcesDirectories)
{
	reporter.out("resdir %s => %s", resDir.key, resDir.value)
  def rdn = FilePaths.normalizePath(resDir.key)
  def rdf = new File(rdn)
  def currentResourcesAbsolutePath = FilePaths.normalizePath(rdf.absolutePath)
  def dst = resDir.value
  if (dst == null) {
	  reporter.out("resource null, continue...")
	  continue
  }
  def dd = (dst.trim() != '') ? "${resourcesDestinationDirectoryName}/${dst}" : resourcesDestinationDirectoryName
  def ddf = new File(dd)
  def currentDestinationAbsolutePath = FilePaths.normalizePath(ddf.absolutePath)
  reporter.debug("[R] ${currentResourcesAbsolutePath} target path: ${currentDestinationAbsolutePath}")
  if (!rdf.exists()) {
      reporter.warn("resource ${currentResourcesAbsolutePath} not found")
      continue
  }
  if (! rdf.isDirectory()) {
      reporter.debug("rdf %s not a dir...", rdf)
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


