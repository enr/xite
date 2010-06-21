import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

def paths = binding.getVariable("xite_paths")
def configuration = binding.getVariable("xite_config")

def logger = LoggerFactory.getLogger('xite.deploy');

logger.info("starting xite.deploy")

def ftp = configuration.deploy.ftp
logger.info("ftp is {}", ftp)

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

