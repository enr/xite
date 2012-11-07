package com.github.enr.xite.plugins

import com.github.enr.xite.util.Configurations;
import com.github.enr.xite.util.FilePaths;

class ResourcesPlugin extends XiteAbstractPlugin {
    PluginResult init() {

    }

    PluginResult apply() {

        def processResources = configuration.get('resources.filter.enabled')
        def resourcesFiltersFile = configuration.get('resources.filter.properties')

        def prefix =  configuration.get('resources.filter.prefix')
        def suffix =  configuration.get('resources.filter.suffix')

        def substitutionsFilePath = FilePaths.join(sourcePath, resourcesFiltersFile)

        File substitutionsFile = new File(substitutionsFilePath)
        Properties subs = new Properties();
        if (substitutionsFile.isFile()) {
            FileInputStream inp = new FileInputStream(substitutionsFilePath);
            subs.load(inp);
            inp.close();
        }

        def resourcesSourceDirectoryName = FilePaths.join(sourcePath, configuration.get('resources.directory'))
        def resourcesDestinationDirectoryName = destinationPath
        def excludedFilenameSuffix = configuration.get('resources.excludedFilenameSuffix')

        // a map of resources directory -> sub directory of destination
        def resourcesDirectories = Configurations.getAdditionals(configuration.getBulk('resources.sources.additionals'), sourcePath)
        resourcesDirectories.put(resourcesSourceDirectoryName, '')

        for (resDir in resourcesDirectories)
        {
            def rdn = FilePaths.normalizePath(resDir.key)
            def rdf = new File(rdn)
            def currentResourcesAbsolutePath = FilePaths.normalizePath(rdf.absolutePath)
            def dst = resDir.value
            if (dst == null) {
                reporter.debug("resource null, continue...")
                continue
            }
            def dd = (dst.trim() != '') ? FilePaths.join(resourcesDestinationDirectoryName, dst) : resourcesDestinationDirectoryName
            def ddf = new File(dd)
            def currentDestinationAbsolutePath = FilePaths.normalizePath(ddf.absolutePath)
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


