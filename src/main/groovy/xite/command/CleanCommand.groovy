package xite.command

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.io.FileUtils;

import xite.XiteAbstractCommand
import xite.api.CommandResult

class CleanCommand extends XiteAbstractCommand
{
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    
    CommandResult execute() {
        def dest = new File(paths.destinationDirectory)
        if (dest.exists()) {
            FileUtils.deleteDirectory(dest)
        }
        def stillAlive = dest.exists()
        if (stillAlive) {
            logger.warn("error deleting directory ${dest.absolutePath}")
        } else {
            logger.info("successfully deleted directory ${dest.absolutePath}")
        }
    }
}


