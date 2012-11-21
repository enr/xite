
app {
    version = "0.1-SNAPSHOT"
    baseContext = '/propsfilter'
    // set to null to use platform default
    encoding = 'UTF-8'
}

project {
    destination = 'target/propsfilter'
}

plugins { 
    enabled = [ 'markdown',
                'resources']
}

properties {
    filter {
        enabled = true
        prefix = '{{'
        suffix = '}}'
    }
}
