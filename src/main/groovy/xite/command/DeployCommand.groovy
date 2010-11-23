package xite.command

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xite.XiteAbstractCommand
import xite.api.CommandResult

class DeployCommand extends XiteAbstractCommand
{
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    CommandResult execute() {
        def ftp = configuration.deploy.ftp
        logger.info("ftp : {}", ftp)
        if (ftp.enabled == true) {
            ant = new AntBuilder()
            
            ant.ftp( 
                server: ftp.host,
                userid: ftp.username,
                password: ftp.password,
                //passive:"yes",
                //verbose:"yes",
                // the ftp action to perform, defaulting to "send"
                // action:"get",
                remotedir: ftp.baseDirectory,
                // selects binary-mode ("yes") or text-mode ("no") transfers. Defaults to "yes"
                binary:"yes" ) {
                    fileset( dir: paths.destinationDirectory ) {
                        //include( name:'* * / *' )
                    }
                }
        }
    }

}


