Feature: Basic commands and options

  Background:
    Given I am the user "Alice"

  Scenario Outline: Get default configuration values
    When I run xite with "config --get <key>" args
    Then the output should contain exactly "<value>"

    Examples:
    | key                           | value                                     |
    | app.encoding                  | UTF-8                                     |
    | project.source                | src/xite                                  |
    | project.destination           | target/xite                               |
    | plugins.enabled               | [code, html, markdown, resources]         |
    | deploy.ftp.enabled            | false                                     |
    | templates.directory           | templates                                 |
    | templates.bottom              | footer.html                               |
    | templates.top                 | header.html                               |
    | resources.directory           | resources                                 |
    | properties.filter.enabled     | true                                      |
    | properties.filter.file        | xite/site.properties                      |
    | properties.filter.prefix      | {{                                        |
    | properties.filter.suffix      | }}                                        |
    | code.enabled                  | true                                      |
    | code.source                   | code                                      |
    | code.destination              | code                                      |
    | code.top                      | templates/header.html                     |
    | code.bottom                   | templates/footer.html                     |
    #| code.template | <pre><code lang="%s">%n%s%n</code></pre> |
    
    
    