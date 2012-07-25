package com.github.enr.xite.plugins


import com.github.enr.markdownj.extras.MarkdownApp
import com.github.enr.xite.util.FilePaths;


class MarkdownPlugin extends XiteAbstractPlugin
{    
    private static final String PLUGIN_SOURCE_BASEDIR = 'markdown'

    PluginResult init() {}

    PluginResult apply() {
        def processableExtensions = configuration.get('markdown.extensions')
        def headerFileName = FilePaths.join(sourcePath, configuration.get('templates.directory'), configuration.get('templates.top'))
        def footerFileName = FilePaths.join(sourcePath, configuration.get('templates.directory'), configuration.get('templates.bottom'))
        def markdownSourceDirectory = FilePaths.normalizePath(FilePaths.join(sourcePath, PLUGIN_SOURCE_BASEDIR))
		def markdownDestinationDirectory = FilePaths.normalizePath(destinationPath)
        def encoding = configuration.get('app.encoding')

		def markdownDirectory = new File(markdownSourceDirectory)
		if (! markdownDirectory.exists()) {
			reporter.warn("markdown directory %s not found... skip generation.", markdownSourceDirectory)
			return
		}

		def headerFile = new File(headerFileName)
		def footerFile = new File(footerFileName)

        def app = new MarkdownApp()
		if (headerFile.exists() && headerFile.isFile()) {
			app.setHeader(headerFileName)
		}
		if (footerFile.exists() && footerFile.isFile()) {
			app.setFooter(footerFileName)
		}
        app.setSource(markdownSourceDirectory)
        if (configuration.get('markdown.code.template')) {
            app.setCodeBlockTemplate(configuration.get('markdown.code.template'))
        }
        if (encoding != null) {
            app.setCharEncoding(encoding)
        }
        //app.strictHtmlEncoding()
        app.setProcessableExtensions(processableExtensions)
        app.setDestination(markdownDestinationDirectory)
        app.process()
    }

    PluginResult cleanup() {}
}


