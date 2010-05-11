
Xite
====


Installing Xite
---------------

At the moment to use Xite you have to build it from the sources.

Prerequisites to the build:

* Gradle


Build command:
If you have Gradle version 0.8:

    gradle  # implies gradle clean app itest

If you have Gradle > 0.8, you can try with:

    gradle -b build-09.gradle
    
This build skips tests.

These commands will build a working installation of Xite in target/app directory

Add 'dist' to the command and you'll have a zip distribution archive.

To try Xite immediately, from the base directory:

    ./target/app/bin/xite
    
This builds Xite site.

To see results:

    ./target/app/bin/xite --port 9090 run

and point your browser to <http://localhost:9090>

For a real installation, move target/app where you like

    mv target/app $HOME/apps/xite

You can use symbolic link to have Xite in your path, without further settings

    ln -s apps/xite/bin/xite /usr/bin/xite





