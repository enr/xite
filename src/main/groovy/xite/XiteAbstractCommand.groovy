package xite

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xite.api.XiteCommand
import xite.api.CommandResult

abstract class XiteAbstractCommand implements XiteCommand
{
    protected Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    
    Paths paths
    ConfigObject configuration
    Map context

    CommandResult init() {
        logger.debug('init()')
    }
    
    abstract CommandResult execute();
    
    CommandResult cleanup() {
        logger.debug('cleanup()')
    }
    
    public String toString() {
        return this.getClass().getName()
    }
}
