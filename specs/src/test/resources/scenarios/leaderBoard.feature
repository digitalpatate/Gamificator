Feature: Basic operations on leaderboard

  Background:
    Given there is a Gamificator server
    And I have a application payload
    When I POST the application payload to the /applications endpoint
    Then I have an API key

  Scenario: get the leaderboard of a pointScale
    Given there is 1 point scale payload
    And I POST the point scale payload to the /pointScales endpoint
    And I retrieve the last created point scale name
    And I GET all point scales and badges created
    And there is 1 rule payload
    When I POST the rule payload to the /rules endpoint
    And I retrieve the last created rule
    And I create 2 events triggering this rule for 2 differents users
    And I create 5 events triggering this rule for 2 differents users
    Then I get the leaderboard of the last created point scale
    And I receive the correct leaderboard