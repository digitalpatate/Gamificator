package ch.heigvd.amt.gamificator.api.spec.steps;


import ch.heigvd.amt.gamificator.ApiException;
import ch.heigvd.amt.gamificator.ApiResponse;
import ch.heigvd.amt.gamificator.api.dto.*;
import ch.heigvd.amt.gamificator.api.dto.ActionDTO;
import ch.heigvd.amt.gamificator.api.dto.AwardPointDTO;
import ch.heigvd.amt.gamificator.api.dto.ConditionDTO;
import ch.heigvd.amt.gamificator.api.dto.CreateEventCommand;
import ch.heigvd.amt.gamificator.api.dto.LeaderBoardDTO;
import ch.heigvd.amt.gamificator.api.dto.PointScaleCreateCommand;
import ch.heigvd.amt.gamificator.api.dto.PointScaleDTO;
import ch.heigvd.amt.gamificator.api.dto.RuleCreateCommand;
import ch.heigvd.amt.gamificator.api.spec.helpers.Environment;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.OffsetDateTime;
import java.util.*;

import static org.junit.Assert.*;

public class LeaderboardSteps extends Steps {
    private String lastCreatedPointScaleName;
    private HashMap<String, Long> scores;

    public LeaderboardSteps(Environment environment) {
        super(environment);
    }

    @And("I receive the correct leaderboard")
    public void iReceiveTheCorrectLeaderboard() {
        LeaderBoardDTO leaderBoardDTO = (LeaderBoardDTO) getEnvironment().getLastApiResponse().getData();

        assertNotNull(leaderBoardDTO);
    }

    @And("I get the leaderboard of the last created point scale")
    public void iGetTheLeaderboardOfTheLastCreatedPointScale() {
        try {
            ApiResponse apiResponse = getApi().getLeaderboardWithHttpInfo(lastCreatedPointScaleName);
            getEnvironment().processApiResponse(apiResponse);
        } catch (ApiException e) {
            getEnvironment().processApiException(e);
        }
    }

    @And("I create {int} events triggering this rule for {int} differents users")
    public void iCreateEventsTriggeringThisRuleForDifferentsUsers(int nbEvents, int nbUsers) {
        for(int user = 0; user < nbUsers; ++user) {
            String uuid = "9723f086-a2f5-41af-b8a1-" + String.format("%016d", user);

            for(int event = 0; event < nbEvents; ++event) {
                CreateEventCommand createEventCommand = new CreateEventCommand();
                createEventCommand.setUserUUID(UUID.fromString(uuid));
                createEventCommand.setType("my first post "/* + counter*/);
                createEventCommand.setTimestamp(OffsetDateTime.now());

                try {
                    getApi().createEvent(createEventCommand);
                } catch (ApiException e) {
                    e.printStackTrace();
                }

                long currentScore = scores.get(uuid);

                /*if(currentScore != null) {

                }*/
            }
        }
    }

    @And("I retrieve the last created point scale name")
    public void iRetrieveTheLastCreatedPointScaleName() {
        PointScaleDTO pointScaleDTO = (PointScaleDTO) getEnvironment().getLastApiResponse().getData();
        lastCreatedPointScaleName = pointScaleDTO.getName();
    }

    @And("I retrieve the last created rule")
    public void iRetrieveTheLastCreatedRule() {
        //getApi().getR
    }
}
