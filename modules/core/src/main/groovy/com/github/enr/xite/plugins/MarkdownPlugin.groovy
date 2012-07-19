package com.github.enr.xite.plugins

import xite.FilePaths

import com.github.enr.markdownj.extras.MarkdownApp


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

        reporter.out("headerFileName '%s'", headerFileName)
        reporter.out("footerFileName '%s'", footerFileName)
        reporter.out("markdownSourceDirectory ${markdownSourceDirectory}")
        reporter.out("markdownDestinationDirectory ${markdownDestinationDirectory}")
        reporter.debug("processableExtensions ${processableExtensions}")
        reporter.debug("configuration.markdown.code.template ${configuration.get('markdown.code.template')}")
        reporter.debug("encoding ${encoding}")
		
		def markdownDirectory = new File(markdownSourceDirectory)
		if (! markdownDirectory.exists()) {
			reporter.out("markdown directory %s not found... skip generation.", markdownSourceDirectory)
			return
		}

		reporter.out("___ 0")
		def headerFile = new File(headerFileName)
		def footerFile = new File(footerFileName)
		
		reporter.out("___ 1")
        def app = new MarkdownApp()
		reporter.out("___ 2")
		if (headerFile.exists()) {
			app.setHeader(headerFileName)
		}
		reporter.out("___ 3")
		if (footerFile.exists()) {
			app.setFooter(footerFileName)
		}
		reporter.out("___ 4")
        app.setSource(markdownSourceDirectory)
        if (configuration.get('markdown.code.template')) {
            reporter.debug('   app.setCodeBlockTemplate(configuration.markdown.code.template)')
            app.setCodeBlockTemplate(configuration.get('markdown.code.template'))
        }
		reporter.out("___ 5")
        if (encoding != null) {
            app.setCharEncoding(encoding)
        }
		reporter.out("___ 6")
        //app.strictHtmlEncoding()
        app.setProcessableExtensions(processableExtensions)
        app.setDestination(markdownDestinationDirectory)
		reporter.out("___ 7")
        app.process()
        reporter.out(' >>>>>>   finished markdown processing')
    }

    PluginResult cleanup() {}
}


