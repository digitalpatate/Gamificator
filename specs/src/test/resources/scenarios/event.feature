Feature: Basic operations on events

  Background:
    Given there is a Gamificator server
    And I have a application payload
    When I POST the application payload to the /applications endpoint
    Then I have an API key

  Scenario: create a new event
    Given there is 1 point scale payload
    When I POST the point scale payload to the /pointScales endpoint
    Then I receive a 201 status code
    And I receive the last created point scale
    Given there is 1 badge payload
    And I POST the badge payload to the /badges endpoints
    Then I receive a 201 status code
    And I receive the last created badge
    Given I GET all point scales and badges created
    And there is 1 rule payload
    When I POST the rule payload to the /rules endpoint
    Then I receive a 201 status code
    And I get the last created rule
    Given there is 1 event payload
    When I POST the event payload to the /events endpoint
    Then I receive a 201 status code