// We can group settings.
// The following settings are created:
// app.version, app.setting1, app.setting2, app.setting3, app.date, app.active
app {
    version = "1.0"
    
    // We can write code to set up settings.
    [1, 2, 3].each {
        this."setting${it}" = it * 10
    }
    
    // We can use Java objects
    date = new Date()
    active = true
}

//def a = binding.getVariable("a")
b = a
//c = binding.getVariable("a")

server.URL = 'http://default'

// Environment specific settings override settings with the
// same name.
environments {
    development {
        server.URL = 'http://localhost'
    }
    test {
        server.URL = 'http://test:9080'
    }
    integrationtest {
        server.URL = 'http://integrationtest/url'
    }
    production {
        server.URL = 'http://prod/url'
    }
}

