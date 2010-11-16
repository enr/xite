import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xite.Paths

def paths = binding.getVariable("xite_paths")
def configuration = binding.getVariable("xite_config")

//System.setProperty('log4j.configuration', 'xite-logging.properties')


def logger = LoggerFactory.getLogger('xite.init');



def sharedLogger = LoggerFactory.getLogger('xite.main');
//binding.setVariable("xite_logger", logger);

logger.info("starting xite.init")
