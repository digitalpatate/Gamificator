Feature: Basic operations on point scale

  Background:
    Given there is a Gamificator server

#  Scenario: get a point scale by id
#    Given there is a point scale payload
#    And I POST the point scale payload to the /pointScales endpoint
#    When I GET the point scale with the id 1
#    Then I receive a 200 status code
#    And I receive the created point scale
#
#  Scenario: create a point scale
#    Given there is a point scale payload
#    When I POST the point scale payload to the /pointScales endpoint
#    Then I receive a 201 status code
#    And I receive the created point scale
#
#  Scenario: get all point scale
#    Given there is a point scale payload
#    And I POST the point scale payload to the /pointScales endpoint
#    When I send a GET to the pointscales endpoint
#    Then I receive a 200 status code
#    And I receive 2 pointscales with differents id