Feature: Basic commands and options

  Background:
    Given I am the user "Alice"

  Scenario: Run --version
    When I run xite with "--version" args
    Then the output should contain exactly "Xite version 0.3-SNAPSHOT"
