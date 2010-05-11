Xite
====

Xite is a static sites generator.

To build from sources you need Gradle 0.8:

    gradle

If you have Gradle > 0.8, you can try with:

    gradle -b build-09.gradle
    
This build skips tests.

To try Xite, from the base directory:

    ./target/app/bin/xite
    
This builds Xite site.

To see results:

    ./target/app/bin/xite --port 9090 run

and point your browser to <http://localhost:9090>

Documentation, in markdown format, is in src/xite/markdown directory.


License
-------

Xite sources contains SHJS <http://shjs.sourceforge.net>, used for the rendering of its site.

SHJS is distributed under the GNU General Public License version 3.



