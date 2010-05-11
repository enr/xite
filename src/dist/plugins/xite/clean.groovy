import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.io.FileUtils;

def paths = binding.getVariable("xite_paths")
def configuration = binding.getVariable("xite_config")



def logger = LoggerFactory.getLogger('xite.clean');

logger.info("starting xite.clean")

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

