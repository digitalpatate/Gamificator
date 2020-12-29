Feature: Basic operations on rules

  Background:
    Given there is a Gamificator server
    And I have a application payload
    When I POST the application payload to the /applications endpoint
    Then I have an API key

  Scenario: create a rule
    Given there is 1 point scale payload
    When I POST the point scale payload to the /pointScales endpoint
    Then I receive a 201 status code
    And I receive the created point scale
    Given there is 1 badge payload
    And I POST the badge payload to the /badges endpoints
    Then I receive a 201 status code
    And I receive the created badge
    Given I GET all point scales and badges created
    And there is 1 rule payload
    When I POST the rule payload to the /rules endpoint
    Then I receive a 201 status code
    And I receive the last created rule

  Scenario: get all rules
    Given there is 1 point scale payload
    When I POST the point scale payload to the /pointScales endpoint
    Then I receive a 201 status code
    And I receive the created point scale
    Given there is 1 badge payload
    And I POST the badge payload to the /badges endpoints
    Then I receive a 201 status code
    And I receive the created badge
    Given I GET all point scales and badges created
    And there is 10 rule payload
    When I POST the rule payload to the /rules endpoint
    Then I receive a 201 status code
    When I GET all rules
    Then I receive 10 rules

  Scenario: update a rule
    Given there is 1 point scale payload
    When I POST the point scale payload to the /pointScales endpoint
    Then I receive a 201 status code
    And I receive the created point scale
    Given there is 1 badge payload
    And I POST the badge payload to the /badges endpoints
    Then I receive a 201 status code
    And I receive the created badge
    Given I GET all point scales and badges created
    And there is 1 rule payload
    When I POST the rule payload to the /rules endpoint
    Then I receive a 201 status code
    And I receive the last created rule
    Given there is 2 point scale payload
    When I POST the point scale payload to the /pointScales endpoint
    Then I receive a 201 status code
    And I receive the created point scale
    Given there is 4 badge payload
    And I POST the badge payload to the /badges endpoints
    Then I receive a 201 status code
    And I receive the created badge
    Given I GET all point scales and badges created
    And there is 1 rule payload
    And I PUT the last created rule payload to the /rules endpoint
    Then I receive a 200 status code
