Feature: Version

  Background:
    Given I am the user "Alice"

  Scenario: Show the current version of Xite
    When I run xite --version
    Then the output should contain current build version
   