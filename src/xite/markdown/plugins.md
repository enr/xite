
Plugins
=======

Xite è organizzato a plugins.
quelli core sono:

Markdown
--------

This plugin transform the contents in markdown directory into html pages in the destination directory, maintaining the directory structure and changing file extension to .html


    markdown {
        extensions = []
        // extensions = ['md', 'markdown']
    }

Resources
---------

Copies every file in specified directories into the site root, maintaining the same dir structure.

By default the directory is $source/resources but user can change it or add others directories with the 
resources.sources.additionals directive

You can set the resources plugin to filter resources, processing placeholder words and substituiting them with 

    resources {
        filter {
            enabled = true
            prefix = '_'
            suffix = '_'
        }
        sources {
            // a map of: source directory   -> sub directory (of main destination dir) for these resources
            // if you want resources deploied in the root, leave an empty string as sub directory
            // relative paths are allowed
            // not existing paths are skipped, but the process continue without errors
            additionals = ['d:/dev/resources':'d-dev', '/tmp/blah':'tmp-blah', 'my/root':'']
        }
        excludedFilenameSuffix = ['~']
    }


Variables can be included in your resources. 

These variables, denoted by the prefix and suffix delimiters, will be replaced by the value of the given property written in resources.filter.properties file.

The filter processing, happens only if resources.filter.enabled is setted to true.

Code
----

This plugin htmlize source code.


Jetty
-----

start jetty

    ./target/app/bin/xite run   # default port 8080
    ./target/app/bin/xite --port 9090 run


Plugin development
------------------

creare un plugin

e' una dir messa in xite_home / plugins

se in site esiste una dir che si chiama come il plugin, vengono eseguiti pre/process/post.groovy in plugin dir


prendere come template _plugin-template.groovy

