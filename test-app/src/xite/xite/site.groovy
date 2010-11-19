
app {
    version = "1.0-TEST"
    baseContext = '/xite'
    encoding = 'UTF-8'
}

markdown {
    extensions = ['md']
}

//def adr = System.getProperty("xite.itest.project.rootDir")

resources {
    filter {
        enabled = true
        prefix = '_'
        suffix = '_'
    }
    sources {
        additionals = [ 'test-app/additionals':'add',
                        '/fake/path':'fake-path',
                        'test-app/single/file.html':'asinglefile.html']
    }
    excludedFilenameSuffix = ['~']
}

code {
    baseContext = '/xite'
    excludedFilenameSuffix = ['~']
}
