Feature: Build the PropsFilter website

  Background:
    Given I am the user "Patty"

  Scenario: Build PropsFilter website
    When I build website "propsfilter"
    Then it should exit with value "0"
    And output file "index.html" should contain "/propsfilter/css/main.css"
    And output file "index.html" should contain "<title>PropsFilter Website UAT edition</title>"
    And output file "index.html" should contain "<h1>PropsFilter Website UAT edition</h1>"
    And output file "index.html" should contain "We are PropsFilter Website UAT edition and this is the Property A"
    And output file "index.html" should contain "<span id='websiteName'>PropsFilter Website UAT edition</span>"
    And output file "index.html" should contain "<span id='website.propsfilter.p.a'>Property A</span>"
    