
Plugins
=======

Every Xite functionality is provided by a plugin.

The core plugins (distributed with Xite sources) are markdown, resources, code, and html.

The default configuration section, to set which plugin is enabled, is:

    plugins { 
        enabled = [ 'code',
                    'html',
                    'markdown',
                    'resources']
    }

For every plugin, you can see default configuration, looking at conf/xite-default.groovy in your local installation.

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
            additionals = [ 'd:/dev/resources':'d-dev',
                            '/tmp/blah':'tmp-blah',
                            'my/root':'']
        }
        excludedFilenameSuffix = ['~']
    }

    
The 'sources' property is a map with, as key a source directory and as value a sub directory (relative 
to the main destination dir) for these resources.

If you want resources deploied in the root, leave an empty string as sub directory.

Relative paths are allowed; the full path is built from the source directory.

Not existing paths are skipped, but the process continue without errors, so you can add paths for every system you have access to.

ie 

    additionals = [ 'd:/my/win/resources':'dest', 
                    '/tmp/my/nix/resources':'dest',
                    '/Users/me/my/mac/resources':'dest']


Variables can be included in your resources. 

These variables, denoted by the prefix and suffix delimiters, will be replaced by the value of the given property written in resources.filter.properties file.

The filter processing, happens only if resources.filter.enabled is setted to true.


Code
----

This plugin htmlize source code.

By default process every file in code.source property and put it in code.destination.

The code.baseContext property is used to build links.

User can add additional sources dir (useful for multimodules projects).

    code {
        enabled = true
        source = "code"
        baseContext = ''
        destination = "code"
        top = "templates/header.html"
        bottom = "templates/footer.html"
        template = '<pre><code lang="%s">%n%s%n</code></pre>'
        sources {
            additionals = [:]
            excludes = []
        }
        excludedFilenameSuffix = []
    }


Plugin development
------------------

A plugin is a class implementing `xite.api.XitePlugin`.

To speed process and access some common functionality, you can extends `xite.XiteAbstractPlugin`.


