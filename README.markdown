Xite
====

Xite is a static sites generator.

To build from sources you need Gradle 0.8:

    gradle -q

If you have Gradle > 0.8, you can try with:

    gradle -q -b build-09.gradle
    
This build skips tests.

To try Xite, from the base directory:

    ./target/app/bin/xite
    
This builds Xite site.

To see results:

    ./target/app/bin/xite --port 9090 run

and point your browser to <http://localhost:9090>

Documentation, in markdown format, is in src/xite/markdown directory.

If you have followed the above instructions, you can read them from the local
running Xite site at <http://localhost:9090/xite/index.html>

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



