Feature: Basic operations on rules

  Background:
    Given there is a Gamificator server
    And I have a application payload
    When I POST the application payload to the /applications endpoint
    Then I have an API key

  Scenario: create a rule
    Given there is a point scale payload
    When I POST the point scale payload to the /pointScales endpoint
    Then I receive a 201 status code
    And I receive the created point scale
    Given there is a badge payload
    And I POST the badge payload one to the /badges endpoints
    Then I receive a 201 status code
    And I receive the created badge