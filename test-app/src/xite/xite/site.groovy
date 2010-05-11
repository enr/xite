
app {
    version = "1.0-TEST"
    baseContext = '/xite'
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
        additionals = [ 'additionals':'add', 
                        'test-app/additionals':'add',
                        '/tmp/blah':'tmp-blah']
    }
    excludedFilenameSuffix = ['~']
}

code {
    baseContext = '/xite'
    excludedFilenameSuffix = ['~']
}
