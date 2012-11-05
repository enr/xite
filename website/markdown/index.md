
Xite
====

Xite is a tool for static site generation from source files which can be in various formats.

[Markdown] [1] is supported by default.

At the moment to use Xite you have to build it from the sources using [Gradle] [2].

Go to [installation instructions] [3]


Getting started
---------------

- [installation](/xite/installation.html)
- [usage](/xite/usage.html)
- [plugins](/xite/plugins.html)
- [configuration](/xite/configuration.html)


How tos
-------

- [build your site for xite](/xite/build-your-site-for-xite.html)
- [code highlighting](/xite/code-highlighting.html)
- [xite for software projects](/xite/software-projects.html)


Developer documentation
-----------------------

To get the code and build from source, do the following:

    git clone git://github.com/enr/xite.git
    cd xite
    ./gradlew installApp

To generate Eclipse metadata (.classpath and .project files), do the following:

    ./gradlew eclipse

Once complete, you may then import the projects into Eclipse:

    File -> Import -> Existing projects into workspace

Other developers' resources:

- [dependencies](/xite/dependencies.html)
- [tests results](/xite/developers/tests/index.html)
- [uat](/xite/developers/uat/index.html)
- [xite source code](/xite/code/index.html)
- build and dist (TODO)
- build Xite's site (TODO)
- build manual (TODO)



[1]: http://daringfireball.net/projects/markdown    "Markdown"
[2]: http://gradle.org                              "Gradle"
[3]: /xite/installation.html                        "Xite installation"

