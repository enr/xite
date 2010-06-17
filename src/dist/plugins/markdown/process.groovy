import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.enr.markdownj.extras.MarkdownApp

def paths = binding.getVariable("xite_paths")
def configuration = binding.getVariable("xite_config")


def logger = LoggerFactory.getLogger('xite.markdown');

def processableExtensions = configuration.markdown.extensions


def headerFileName = "${paths.sourceDirectory}/${configuration.templates.directory}/${configuration.templates.top}"
def footerFileName = "${paths.sourceDirectory}/${configuration.templates.directory}/${configuration.templates.bottom}"

def markdownSourceDirectory = "${paths.sourceDirectory}/markdown"

def encoding = configuration.app.encoding

logger.debug("headerFileName ${headerFileName}")
logger.debug("footerFileName ${footerFileName}")
logger.debug("markdownSourceDirectory ${markdownSourceDirectory}")
logger.debug("processableExtensions ${processableExtensions}")
logger.debug("configuration.markdown.code.template ${configuration.markdown.code.template}")
logger.debug("encoding ${encoding}")

def app = new MarkdownApp()
app.setHeader(headerFileName)
app.setFooter(footerFileName)
app.setSource(markdownSourceDirectory)
if (configuration.markdown.code.template) {
    logger.warn('   app.setCodeBlockTemplate(configuration.markdown.code.template)')
    app.setCodeBlockTemplate(configuration.markdown.code.template)
}
if (encoding != null) {
    app.setCharEncoding(encoding)
}
//app.strictHtmlEncoding()
app.setProcessableExtensions(processableExtensions)
app.setDestination(paths.destinationDirectory)
app.process()

