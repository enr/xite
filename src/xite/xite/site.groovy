
app {
    version = "0.1-SNAPSHOT"
    baseContext = '/xite'
    // set to null to use platform default
    encoding = 'UTF-8'
}

// you can override default value for destination directory
project {
    destination = 'target/xite/xite'
}


markdown {
    extensions = ['md']
}

resources {
    filter {
        enabled = true
        prefix = '_'
        suffix = '_'
    }
    sources {
        // a map of: source directory   -> sub directory (of main destination dir) for these resources
        // if you want resources deploied in the root, leave an empty string as sub directory
        // relative paths are allowed
        // not existing paths are skipped, but the process continue without errors, so you can
        // add paths for every system you have access to
        //additionals = ['d:/dev/resources':'d-dev', '/tmp/blah':'tmp-blah']
        additionals = ['./sh':'']
    }
    excludedFilenameSuffix = ['~']
}

code {
    excludedFilenameSuffix = ['~']
    sources {
        additionals = ['src':'']
        excludes = ['src/xite/resources',
                    'src/xite/markdown/',
                    'src/xite/templates']
    }
    baseContext = '/xite'
    //template = '<pre class="brush: %s">\n%s\n</pre>'; // http://alexgorbatchev.com/wiki/SyntaxHighlighter
    //template = '<pre class="sh_%s">\n%s\n</pre>'; // http://shjs.sourceforge.net/doc/documentation.html
    template = '<script type="syntaxhighlighter" class="brush: %s"><![CDATA[%s]]></script>'; // http://alexgorbatchev.com/wiki/SyntaxHighlighter
    top = "templates/header-code.html"
    bottom = "templates/footer-code.html"
}
