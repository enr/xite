
Xite configuration
==================

Xite configuration occurs at 3 levels:

  * Project - $SOURCE_DIRECTORY/xite/site.groovy
  * Installation - this is configuration added once for a Xite installation 
    and used where not overriden $XITE_HOME/conf/xite-default.groovy
  * User - this is configuration specific to a particular user
    $HOME/.xite/settings.groovy


Default settings
----------------

If settings are not overriden they are taken from $XITE_HOME/conf/xite-default.groovy.

The file is commented.
    

User settings
-------------

Configurations data that should not be bundled to any specific project, or 
distributed to an audience.

These include values such as authentication information for the deployment.

The file is $HOME/.xite/settings.groovy


Environment
----------

Xite is environment friendly :)

You can override specific conf values using the environment section in one of the
configuration files (tech note: using the standard Groovy ConfigSlurper syntax)

Customize
---------

To see every configuration data, the best way is to read the default file in $XITE_HOME/conf/xite-default.groovy


