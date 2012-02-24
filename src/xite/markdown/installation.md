
Installing Xite
===============

At the moment to use Xite you have to build it from the sources.

Build command:

    ./gradlew -q

This command will build a working installation of Xite in target/app directory

Add 'dist' to the command and you'll have a zip distribution archive.

    ./gradlew dist

To try Xite immediately, from the base directory:

    ./target/app/bin/xite
    
This builds Xite site.

To see results:

    ./target/app/bin/xite --port 9090 run

and point your browser to <http://localhost:9090/xite>

For a real installation, move target/app where you like

    mv target/app $HOME/apps/xite

You can use symbolic link to have Xite in your path, without further settings

    ln -s $HOME/apps/xite/bin/xite /usr/bin/xite





