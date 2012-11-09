Feature: Build the actual Xite's website

  Background:
    Given I am the user "Bob"

  Scenario: Build actual Xite's website
    When I build the actual Xite's website
    Then it should exit with value "0"
    And html files should be generated from markdown
    And resources are copied from standard path
    

  #Scenario: Run actual Xite's website
  #  Given the website was built in "target/website"
  #  When I run xite script with "serve --root target/website --port 9191" args
  #  Then the url "http://127.0.0.1:9191/xite" should be valid
  #  And I close process
