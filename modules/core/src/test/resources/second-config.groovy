
app {
    version = "2.0"
    
    // We can write code to set up settings.
    [1, 2, 3].each {
        this."setting${it}" = it * 3
    }
    
    // We can use Java objects
    date = new Date()
    active = true
}

// Classic way of specifying properties without grouping, 
// but we can use Java objects instead of only strings.
mail.smtp.host = "smtpservername"
mail.smtp.ssl = true

