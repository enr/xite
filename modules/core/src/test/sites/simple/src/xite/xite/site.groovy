
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
        additionals = [ '../../additionals':'add',
                        '/fake/path':'fake-path',
                        '../../single/file.html':'asinglefile.html']
    }
    excludedFilenameSuffix = ['~']
}

code {
    baseContext = '/simple'
    excludedFilenameSuffix = ['~']
}
