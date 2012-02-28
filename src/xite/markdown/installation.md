
Installing Xite
===============

At the moment to use Xite you have to build it from the sources.

Build command:

    ./gradlew -q

This command will build a working installation of Xite in target/install/xite directory

To try Xite immediately, from the base directory:

    ./target/install/xite/bin/xite
    
This builds Xite site.

To see results:

    ./target/install/xite/bin/xite --port 9090 run

and point your browser to <http://localhost:9090/xite>

For a real installation, move target/install/xite where you like

    mv target/install/xite $HOME/apps/xite

You can use symbolic link to have Xite in your path, without further settings

    ln -s $HOME/apps/xite/bin/xite /usr/bin/xite





