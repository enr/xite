package com.github.enr.xite.plugins

import com.github.enr.markdownj.extras.MarkdownApp


class MarkdownPlugin extends XiteAbstractPlugin
{    
    private static final String PLUGIN_SOURCE_BASEDIR = 'markdown'

    PluginResult init() {}

    PluginResult apply() {
        def processableExtensions = configuration.get('markdown.extensions')
        def headerFileName = "${sourcePath}/${configuration.get('templates.directory')}/${configuration.get('templates.top')}"
        def footerFileName = "${sourcePath}/${configuration.get('templates.directory')}/${configuration.get('templates.bottom')}"
        def markdownSourceDirectory = "${sourcePath}/${PLUGIN_SOURCE_BASEDIR}"
        def encoding = configuration.get('app.encoding')

        reporter.debug("headerFileName ${headerFileName}")
        reporter.debug("footerFileName ${footerFileName}")
        reporter.debug("markdownSourceDirectory ${markdownSourceDirectory}")
        reporter.debug("processableExtensions ${processableExtensions}")
        reporter.debug("configuration.markdown.code.template ${configuration.get('markdown.code.template')}")
        reporter.debug("encoding ${encoding}")

        def app = new MarkdownApp()
        app.setHeader(headerFileName)
        app.setFooter(footerFileName)
        app.setSource(markdownSourceDirectory)
        if (configuration.get('markdown.code.template')) {
            reporter.debug('   app.setCodeBlockTemplate(configuration.markdown.code.template)')
            app.setCodeBlockTemplate(configuration.get('markdown.code.template'))
        }
        if (encoding != null) {
            app.setCharEncoding(encoding)
        }
        //app.strictHtmlEncoding()
        app.setProcessableExtensions(processableExtensions)
        app.setDestination(destinationPath)
        app.process()
        reporter.debug('finished markdown processing')
    }

    PluginResult cleanup() {}
}


