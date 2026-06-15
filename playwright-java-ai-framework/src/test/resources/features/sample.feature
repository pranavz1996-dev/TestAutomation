Feature: Automation Exercise Website

  Scenario: Verify home page loads successfully
    Given I am on the home page
    Then the page title should contain "Automation Exercise"
