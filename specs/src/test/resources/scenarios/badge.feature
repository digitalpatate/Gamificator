Feature: Basic operations on badges

  Background:
    Given there is a Gamificator server
    And I have a application payload
    When I POST the application payload to the /applications endpoint
    Then the I have an API key

  Scenario: get a badge by id
    Given there is a badge payload
    And I POST the badge payload to the /badges endpoints
    When I GET the badge with the id 1
    Then I receive a 200 status code
    And I receive the created badge

  Scenario: create a badge
    Given there is a badge payload
    And I POST the badge payload to the /badges endpoints
    Then I receive a 201 status code
    And I receive the created badge
