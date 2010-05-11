import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// this is a template for xite plugin

def paths = binding.getVariable("xite_paths")
def configuration = binding.getVariable("xite_config")

def logger = LoggerFactory.getLogger('xite.plugin.template');

logger.info("this is a template for xite plugin")




