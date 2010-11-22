
Xite FAQ
========


How can I test the site without deploy to a web server?
-------------------------------------------------------

    xite run

Now you can browse your site at http://localhost:8080

You can pass the port number to use:

    xite --port 9090 run


How can I use Maven reports in my Xite site?
--------------------------------------------

Add to resources configuration every Maven site target directory and ensure to
execute mvn site before the xite command


How to avoid the recursive code processing of files in Xite dir?
----------------------------------------------------------------

Add the directory you want skipped and write down them into the configuration file.

Taken from Xite src/xite/site.groovy:

    sources {
        additionals = ['src':'']
        excludes = ['src/xite/resources',
                    'src/xite/markdown/',
                    'src/xite/templates']
    }

Maybe this will be the default behaviour in future releases.


