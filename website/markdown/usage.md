
Xite usage
==========

Follow <a href="/xite/installation.html">install instructions</a>.

Xite offers you commands to build your site, run the site for testing purposes, and deploy the site via FTP.

Build the site
--------------

The build command is the default, so you have only to provide source and destination directories.

Run Xite with -s 'source directory' -d 'destination directory'

    /path/to/xite -s website -d /var/www/site
    
If source or destination are not provided, Xite assumes them as src/xite and target/xite/$app-name


Run the site
------------

The run command is intended for testing purpose.

It fires an embedded Jetty server.

To see results:
    
    /path/to/xite --port 9090 run
    
The port is optional, if not specified it will be 8080.


Deploy the site
---------------

The deploy command, transfer your site to a remote server via FTP.

You have to tell Xite username and password, and set the deploy.ftp.enabled property to true.

For a shared project, the best way to do it, is to set credentials in home/.xite/settings.groovy


    deploy {
        ftp {
            enabled = true
            username = 'Us3rn4me'
            password = 'p455w0rd'
            host = 'my.host'
            baseDirectory = '/var/www/the-site-root'
        }
    }

The base directory should exists.


