
app {
    version = "1.0-SNAPSHOT"
    baseContext = '/simple'
    encoding = 'UTF-8'
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
        additionals = [ 'samples/simple/additionals':'add',
                        '/fake/path':'fake-path',
                        'samples/simple/single/file.html':'asinglefile.html']
    }
    excludedFilenameSuffix = ['~']
}

code {
    baseContext = '/simple'
    excludedFilenameSuffix = ['~']
}
