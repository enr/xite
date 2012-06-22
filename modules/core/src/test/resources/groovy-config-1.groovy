

a {
    one = true
}

b = binded + "1"

e = 'e-default'

name = 'Groovy'
groovier = "Hello ${name}!"
    
environments {
    development {
        e = 'e-development'
    }
    test {
        e = 'e-test'
    }
    integration {
        e = 'e-integration'
    }
    production {
        e = 'e-production'
    }
}

