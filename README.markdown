Xite
====

Xite is a static sites generator.


To use Xite, at the moment you have to build it from sources.

To get sources, you can clone the project using Git:

    git clone git://github.com/enr/xite

or download it in either tar or zip formats:

    wget --no-check-certificate https://github.com/enr/xite/tarball/master
    wget --no-check-certificate https://github.com/enr/xite/zipball/master

Xite is built using the Gradle build system, at the moment version 1.0-milestone-3.

You don't need to install Gradle to build the project. The project uses a Gradle provided wrapper that automatically downloads the correct version for you.

To build from sources you can:

    ./gradlew -q

Sometimes, building from sources, you can have "unknown resolver" error messages.

The messages can be ignored. You get them only in the first build after a repository url change.
    
To try Xite, change to the base directory and, after the build command:

    ./target/app/bin/xite
    
This builds Xite's site.

To see results:

    ./target/app/bin/xite --port 9090 run

and point your browser to <http://localhost:9090>

If you have followed the above instructions, you can read it from the local
running Xite site at <http://localhost:9090/xite/index.html>

Some other info on Xite, is at <http://enr.github.com/xite/>, a xite-generated site. 


License
-------

Licensed under the Apache License, Version 2.0 (the "License");
you may not use Xite except in compliance with the License.

You may obtain a copy of the License at

   <http://www.apache.org/licenses/LICENSE-2.0>

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.

See the License for the specific language governing permissions and
limitations under the License.

Other libraries licenses
------------------------

Xite lib folder contains some jar not present in public Maven repositories:

* enr/markdownj <http://github.com/enr/markdownj> distributed under BSD License
    
* markdownj-extras <http://github.com/enr/markdownj-extras> distributed under Apache 2.0

* ReportNG <http://reportng.uncommons.org> distributed under Apache 2.0

