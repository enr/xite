
Xite configuration
==================

Xite configuration occurs at 3 levels:

  * Project - $SOURCE_DIRECTORY/xite/site.groovy
  * Installation - this is configuration added once for a Xite installation 
    and used where not overriden $XITE_HOME/conf/xite-default.groovy
  * User - this is configuration specific to a particular user
    $HOME/.xite/settings.groovy

You can see the actual used paths:

    /path/to/xite config --files

the list of used properties:

    /path/to/xite config --list
    
A single property:

    /path/to/xite config --get plugins.enabled


Default settings
----------------

If settings are not overriden they are taken from $XITE_HOME/conf/xite.groovy.

The file is commented.
    

User settings
-------------

Configurations data that should not be bundled to any specific project, or 
distributed to an audience.

These include values such as authentication information for the deployment.

The file is $HOME/.xite/$XITE_VERSION/xite.groovy


Environment
----------

Xite is environment friendly :)

You can override specific conf values using the environment section in one of the
configuration files (tech note: using the standard Groovy ConfigSlurper syntax)

Customize
---------

To see every configuration data, the best way is to read the default file in $XITE_HOME/conf/xite-default.groovy

Template variables (or properties filter)
-----------------------------------------

Xite allows properties filtering.

The relevant configuration is (default):


    properties {
        filter {
            enabled = true
            // relative from the sources directory
            file = "xite/site.properties"
            prefix = '{{'
            suffix = '}}'
        }
    }

With this configuration, each property in `properties.filter.file` will be used to replace variables in your website files.

Eg for property `key=value` the string `{{key}}` will be replaced from `value`.

Other than the properties in `properties.filter.file` Xite will replace some implicit variable such as `app.baseContext`;
this mean you can write something as

    <link href="{{app.baseContext}}/css/main.css" media="screen" rel="stylesheet" type="text/css" />
    
in your templates and enjoy the correct path.

Properties filter is done as final step of the build command, over files with name ending in .html, .css and .js .

