package xite.plugins

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;

import java.nio.charset.Charset

import xite.HtmlDirectoryLister;
import xite.Strings;
import xite.Files;
import xite.FilePaths;

import xite.XiteAbstractPlugin
import xite.api.PluginResult

/**
 * Generates copy of code files in HTML format, keeping the sources directory structure.
 *
 */
class CodePlugin extends XiteAbstractPlugin
{
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    
    PluginResult init() {}

    PluginResult apply() {

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
        logger.warn('configuration.code.sources.additionals: {}', configuration.code.sources.additionals)
        for (additional in configuration.code.sources.additionals) {
            codeSourceDirectories.put(paths.sourceDirectory + '/' + additional.key, additional.value)
        }

        logger.debug("headerFileName ${headerFileName}")
        logger.debug("footerFileName ${footerFileName}")
        logger.warn("codeSourceDirectories ${codeSourceDirectories}")
        logger.warn("codeDestinationDirectory ${codeDestinationDirectory}")

        for (codeDir in codeSourceDirectories) {
          def cs = paths.normalize(codeDir.key)
          def csf = new File(cs)
          def currentCodeAbsolutePath = paths.normalize(csf.absolutePath)
          def dst = codeDir.value
          def dd = (dst.trim() != '') ? "${codeDestinationDirectory}/${dst}" : codeDestinationDirectory
          def ddf = new File(dd)
          def currentDestinationAbsolutePath = paths.normalize(ddf.absolutePath)
          logger.warn("processing dir ${currentCodeAbsolutePath}, target dir: ${currentDestinationAbsolutePath}")
        
          if (!csf.exists()) {
              logger.warn("source directory ${currentCodeAbsolutePath} not found")
              continue
          }
		  
		  File indexFile = new File(ddf, "index.html")
		  if (indexFile.exists()) {
			  indexFile.delete()
		  }
          
          csf.eachFileRecurse() { src ->
            def fap = paths.normalize(src.absolutePath)
			logger.info("code ${fap}")
            for (efs in excludedFilenameSuffix) {
                if (fap.endsWith(efs)) {
                    logger.warn("skipping ${fap} (${efs})")
                    return
                }
            }
            // TODO: for rd in excluded dirs...
            // xite sources dir is skipped
            for (excluded in excludedDirs) {
              def rd = paths.normalize(new File(excluded).getAbsolutePath())
              if (fap.startsWith(rd)) {
                logger.warn("skipping file ${fap} in excluded dir")
                return
              }
            }
            logger.info(" F    ${fap}")
            logger.info(" S    ${currentCodeAbsolutePath}")
            logger.info(" D    ${currentDestinationAbsolutePath}")
            def extension = FilePaths.extension(fap.toString())
            // what if file has no extension??
            def lang = codeLang(extension)
            logger.debug("lang : ${lang}")
            // file extension is taken as lang
            def destinationFileNameNoHtmlExtension = fap.replace(currentCodeAbsolutePath, currentDestinationAbsolutePath)
            logger.debug("         replace : ${currentCodeAbsolutePath}")
            def fileTitle = fap.replace(currentCodeAbsolutePath, "")
            logger.debug("         fileTitle : ${fileTitle}")
            def destinationFileName = "${destinationFileNameNoHtmlExtension}.html"
            logger.warn("destinationFileName : ${destinationFileName}")
            def destinationFile = new File(destinationFileName)
            if (destinationFile.exists()) { destinationFile.delete() }
            def destinationFileNoHtmlExtension = new File(destinationFileNameNoHtmlExtension)
          
            if (src.isDirectory()) {
              if (!destinationFileNoHtmlExtension.exists()) {
                  destinationFileNoHtmlExtension.mkdirs()
              }
            }
            
            if (src.isFile()) {
              def parentDirectory = new File(destinationFile.parent)
              if (!parentDirectory.exists()) {
                def success = parentDirectory.mkdirs()
                assert success 
              }
              String codeString = src.getText(encoding);
              def content = String.format(configuration.code.template, lang, codeString)
              def headerWithHeading = "${header}<p/><h3>${fileTitle}</h3><p/>"
              String finalContent = Strings.normalizeEol("${headerWithHeading}${content}${footer}");
			  Files.write(destinationFile, finalContent, Charset.forName(encoding));
            }
          }
        }

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


