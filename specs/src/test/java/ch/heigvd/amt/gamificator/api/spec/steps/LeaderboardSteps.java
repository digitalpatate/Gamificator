package ch.heigvd.amt.gamificator.api.spec.steps;


import ch.heigvd.amt.gamificator.ApiException;
import ch.heigvd.amt.gamificator.ApiResponse;
import ch.heigvd.amt.gamificator.api.dto.AwardPointDTO;
import ch.heigvd.amt.gamificator.api.dto.CreateEventCommand;
import ch.heigvd.amt.gamificator.api.dto.LeaderBoardDTO;
import ch.heigvd.amt.gamificator.api.dto.PointScaleDTO;
import ch.heigvd.amt.gamificator.api.dto.RuleDTO;
import ch.heigvd.amt.gamificator.api.dto.UserDTO;
import ch.heigvd.amt.gamificator.api.dto.UserScoreDTO;
import ch.heigvd.amt.gamificator.api.spec.helpers.Environment;
import io.cucumber.java.en.And;


import java.time.OffsetDateTime;
import java.util.*;

import static org.junit.Assert.*;

public class LeaderboardSteps extends Steps {
    private String lastCreatedPointScaleName;
    private HashMap<UUID, Integer> scores = new HashMap<>();
    private RuleDTO lastCreatedRule;

    public LeaderboardSteps(Environment environment) {
        super(environment);
    }

    @And("I receive the correct leaderboard")
    public void iReceiveTheCorrectLeaderboard() {
        LeaderBoardDTO leaderBoardDTO = (LeaderBoardDTO) getEnvironment().getLastApiResponse().getData();
        assertNotNull(leaderBoardDTO);

        List<UserScoreDTO> userScoreDTOS = new LinkedList<>();

        scores.forEach((k,v) -> {
            try {
                ApiResponse apiResponse = getApi().getUserWithHttpInfo(k);
                getEnvironment().processApiResponse(apiResponse);
            } catch (ApiException e) {
                getEnvironment().processApiException(e);
            }

            UserDTO userDTO = (UserDTO) getEnvironment().getLastApiResponse().getData();

            UserScoreDTO userScoreDTO = new UserScoreDTO();
            userScoreDTO.setUser(userDTO);
            userScoreDTO.setScore(v);
            userScoreDTOS.add(userScoreDTO);
        });

        userScoreDTOS.sort(Comparator.comparing(UserScoreDTO::getScore).reversed().thenComparing(u -> u.getUser().getUuid()));
        LeaderBoardDTO expectedLeaderboardDTO = new LeaderBoardDTO();
        expectedLeaderboardDTO.setLeaderboard(userScoreDTOS);

        assertEquals(expectedLeaderboardDTO, leaderBoardDTO);
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
        List<AwardPointDTO> awardPointDTOs = lastCreatedRule.getThen().getAwardPoints();
        AwardPointDTO awardPointDTO = awardPointDTOs.get(awardPointDTOs.size() - 1);
        int pointsAwarded = awardPointDTO.getValue();

        int initialNbOfUsers = scores.size();

        for(int user = initialNbOfUsers; user < initialNbOfUsers + nbUsers; ++user) {
            UUID uuid = UUID.fromString("22d89507-be5b-48fc-8779-" + String.format("%012d", user));

            for(int event = 0; event < nbEvents; ++event) {
                CreateEventCommand createEventCommand = new CreateEventCommand();
                createEventCommand.setUserUUID(uuid);
                createEventCommand.setType(lastCreatedRule.getCondition().getType());
                createEventCommand.setTimestamp(OffsetDateTime.now());

                try {
                    getApi().createEvent(createEventCommand);
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }

            int score = nbEvents * pointsAwarded;
            Integer currentScore = scores.get(uuid);

            if(currentScore != null) {
                score += currentScore;
            }

            scores.put(uuid, score);
        }
    }

    @And("I retrieve the last created point scale name")
    public void iRetrieveTheLastCreatedPointScaleName() {
        PointScaleDTO pointScaleDTO = (PointScaleDTO) getEnvironment().getLastApiResponse().getData();
        lastCreatedPointScaleName = pointScaleDTO.getName();
    }

    @And("I retrieve the last created rule")
    public void iRetrieveTheLastCreatedRule() {
        lastCreatedRule = (RuleDTO) getEnvironment().getLastApiResponse().getData();
    }
}
