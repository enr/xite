Feature: Clean command

  Background:
    Given I am the user "Bob"

  Scenario: Clean output directory
    When I build the actual Xite's website
    And I run xite with "clean" args
    Then it should exit with value "0"
    And directory "target/uat" should not exist
    