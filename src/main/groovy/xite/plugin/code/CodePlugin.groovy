package xite.plugin.code

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.io.FileUtils;

import xite.HtmlDirectoryLister;
import xite.Strings;
import xite.Files;

import xite.XiteAbstractPlugin
import xite.api.PluginResult

/**
 * Generates copy of code files in HTML format, keeping the sources directory structure.
 *
 */
class CodePlugin extends XiteAbstractPlugin
{
    PluginResult init() {}
    PluginResult apply() {

def logger = LoggerFactory.getLogger('xite.code');

def excludedFilenameSuffix = configuration.code.excludedFilenameSuffix

// todo if code header/footer not in config, use default header/footer
def headerFileName = "${paths.sourceDirectory}/${configuration.code.top}"
def footerFileName = "${paths.sourceDirectory}/${configuration.code.bottom}"

def baseContext = "${configuration.code.baseContext}"

def excludedDirs = configuration.code.sources.excludes

def encoding = configuration.app.encoding

def header = new File(headerFileName).getText(encoding)
def footer = new File(footerFileName).getText(encoding)

def codeSourceDirectory = "${paths.sourceDirectory}/${configuration.code.source}"
def codeDestinationDirectory = "${paths.destinationDirectory}/${configuration.code.destination}"
// a map of resources directory -> sub directory of destination
def codeSourceDirectories = [:]
codeSourceDirectories.put(codeSourceDirectory, '')
logger.debug('configuration.code.sources.additionals: {}', configuration.code.sources.additionals)
for (a in configuration.code.sources.additionals) {
    codeSourceDirectories.put(a.key, a.value)
}

logger.debug("headerFileName ${headerFileName}")
logger.debug("footerFileName ${footerFileName}")
logger.debug("codeSourceDirectories ${codeSourceDirectories}")
logger.debug("codeDestinationDirectory ${codeDestinationDirectory}")

for (codeDir in codeSourceDirectories) {
  def cs = paths.normalize(codeDir.key)
  def csf = new File(cs)
  def currentCodeAbsolutePath = paths.normalize(csf.absolutePath)
  def dst = codeDir.value
  def dd = (dst.trim() != '') ? "${codeDestinationDirectory}/${dst}" : codeDestinationDirectory
  def ddf = new File(dd)
  def currentDestinationAbsolutePath = paths.normalize(ddf.absolutePath)
  logger.debug("\nprocessing dir ${currentCodeAbsolutePath}\ntarget dir: ${currentDestinationAbsolutePath}")

  if (!csf.exists()) {
      logger.warn("source directory ${currentCodeAbsolutePath} not found")
      continue
  }
  
  csf.eachFileRecurse() { src ->
    def fap = paths.normalize(src.absolutePath)
    for (efs in excludedFilenameSuffix) {
        if (fap.endsWith(efs)) {
            logger.info("skipping ${fap} (${efs})")
            return
        }
    }
    
    // TODO: for rd in excluded dirs...
    // xite sources dir is skipped
    for (excluded in excludedDirs) {
      def rd = paths.normalize(new File(excluded).getAbsolutePath())
      if (fap.startsWith(rd)) {
        logger.debug("skipping file ${fap} in excluded dir")
        return
      }
    }

    
    //logger.debug("processing ${fap}")
    //logger.debug("replace: ${currentCodeAbsolutePath} . ${currentDestinationAbsolutePath}")
    // qua: trovare estensione file
    def extension = Files.extension(fap)
    // what if file has no extension??
    def lang = codeLang(extension)
    logger.debug("lang : ${lang}")
    // estensione file diventa lang
    // e viene sostituita da html nel destination file path
    def destinationFileNameNoHtmlExtension = fap.replace(currentCodeAbsolutePath, currentDestinationAbsolutePath)
    def destinationFileName = "${destinationFileNameNoHtmlExtension}.html"
    logger.debug("destinationFileName : ${destinationFileName}")
    def destinationFile = new File(destinationFileName)
    if (destinationFile.exists()) { destinationFile.delete() }
    //logger.debug("destinationFileNameNoHtmlExtension : ${destinationFileNameNoHtmlExtension}")
    def destinationFileNoHtmlExtension = new File(destinationFileNameNoHtmlExtension)
  
    if (src.isDirectory()) {
      if (!destinationFileNoHtmlExtension.exists()) {
          destinationFileNoHtmlExtension.mkdirs()
      }
    }
    
    if (src.isFile()) {
      def parentDirectory = new File(destinationFile.parent)
      if (!parentDirectory.exists()) {
        //logger.info("directory ${parentDirectory} NO exists. creating...")
        def success = parentDirectory.mkdirs()
        assert success 
      }
      //def codeString = wrapLines(src.text.toString
      String codeString = src.getText(encoding);
      def content = String.format(configuration.code.template, lang, codeString)
      String finalContent = Strings.normalizeEol("${header}${content}${footer}");
      //logger.warn(finalContent)
      //destinationFile << finalContent
      FileUtils.writeStringToFile(destinationFile, finalContent, encoding) 
    }
  }
}

//IndexPage index = new IndexPage()
//index.header = header
//index.footer = footer
//index.create(new File(codeDestinationDirectory), '/xite/code');
HtmlDirectoryLister lister = new HtmlDirectoryLister.Builder(new File(codeDestinationDirectory))
                            .context("${baseContext}/code")
                            .header(header)
                            .footer(footer)
                            .divId('dir-listing')
                            .directoryCssClass('dir-listing-d')
                            .fileCssClass('dir-listing-f')
                            .build();
lister.write();


        
        
        
    }
   
    
/**
 * Resolves the language from the file extension, returning a lang understood from syntax highlighting js library
 *
 * todo: keep mapping in config.
 *
 */
private String codeLang(String extension)
{
    if (extension == null) return ''
    if (extension == '') return ''
    if (extension == 'sh') return 'shell'
    if (extension == 'py') return 'python'
    return extension
}

}


