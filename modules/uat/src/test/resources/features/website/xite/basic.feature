Feature: Basic commands and options

  Background:
    Given I am the user "Alice"

  Scenario: Run --version
    When I run xite with "--version" args
    Then the output should contain exactly "Xite version 0.3-SNAPSHOT"

  Scenario Outline: List configuration
    When I run xite with "config --get <key>" args
    Then the output should contain exactly "<value>"

    Examples:
    | key                           | value                                     |
    | app.version                   | 0.1-SNAPSHOT                              |
    | app.encoding                  | UTF-8                                     |
    | project.source                | src/xite                                  |
    | project.destination           | target/xite                               |
    | plugins.enabled               | [code, html, markdown, resources]         |
    | deploy.ftp.enabled            | false                                     |
    | templates.directory           | templates                                 |
    | templates.bottom              | footer.html                               |
    | templates.top                 | header.html                               |
    | resources.directory           | resources                                 |
    | resources.filter.enabled      | true                                      |
    | resources.filter.properties   | xite/site.properties                      |
    | code.enabled                  | true                                      |
    | code.source                   | code                                      |
    | code.destination              | code                                      |
    | code.top                      | templates/header.html                     |
    | code.bottom                   | templates/footer.html                     |
    #| code.template | <pre><code lang="%s">%n%s%n</code></pre> |
    
    
    