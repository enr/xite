Feature: Build the actual Xite's website

  Background:
    Given I am the user "Bob"

  Scenario: Build actual Xite's website
    When I build the actual Xite's website
    Then it should exit with value "0"
