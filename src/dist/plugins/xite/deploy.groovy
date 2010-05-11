import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import java.text.DateFormat
//import org.apache.commons.net.ftp.FTPClient



def paths = binding.getVariable("xite_paths")
def configuration = binding.getVariable("xite_config")



def logger = LoggerFactory.getLogger('xite.deploy');

logger.info("starting xite.deploy")

def ftp = configuration.deploy.ftp
logger.info("ftp is {}", ftp)

////////////////////////////////////////////////////////////////////////////////



ant = new AntBuilder()
logger.info("ant is {}", ant)

logger.info('per abilitare ftp scommenta!!!!!!!!!!!!!!!')
//////////ant.ftp( 
//////////    server: ftp.host,
//////////    userid: ftp.username,
//////////    password: ftp.password,
//////////    //passive:"yes",
//////////    //verbose:"yes",
//////////    // the ftp action to perform, defaulting to "send"
//////////    // action:"get",
//////////    remotedir: ftp.baseDirectory,
//////////    // selects binary-mode ("yes") or text-mode ("no") transfers. Defaults to "yes"
//////////    binary:"yes" ) {
//////////        fileset( dir: paths.destinationDirectory ) {
//////////            //include( name:'**/*' )
//////////        }
//////////    }


/* ------------------------------ *
// connect


ftpClient.connect( ftp.host )
ftpClient.login( ftp.username, ftp.password )     //ftp.login( 'username', 'password' )

println "Connected to ${ftp.host}. ${ftpClient.replyString}"

ftpClient.changeWorkingDirectory(ftp.baseDirectory)
/* retrieve file
ftp.changeWorkingDirectory( '.' )  //ftp.changeWorkingDirectory( 'serverFolder' )
file = new File('README.txt') //new File('localFolder' + File.separator + 'localFilename')

file.withOutputStream{ os ->
    ftp.retrieveFile( 'README.txt', os )  //ftp.retrieveFile( 'serverFilename', os )
}
*/
// upload file
//file = new File('otherFile.txt') //new File('localFolder' + File.separator + 'localFilename')
//file.withInputStream{ fis -> ftp.storeFile( 'otherFile.txt', fis ) }
/*
// List the files in the directory
files = ftpClient.listFiles()
println "Number of files in dir: ${files.length}"
df = DateFormat.getDateInstance( DateFormat.SHORT )
files.each{ file ->
    println "${df.format(file.timestamp.time)}\t ${file.name}"
}

// Logout from the FTP Server and disconnect
ftpClient.logout()
ftpClient.disconnect()
// =>
// Connected to localhost. 230 User logged in, proceed.
// Number of files in dir: 2
// 18/01/07  otherFile.txt
// 25/04/06  README.txt
/*---------------------------------- */


