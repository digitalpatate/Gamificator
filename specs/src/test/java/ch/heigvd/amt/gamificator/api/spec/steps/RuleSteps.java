package ch.heigvd.amt.gamificator.api.spec.steps;

import ch.heigvd.amt.gamificator.api.spec.helpers.Environment;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import ch.heigvd.amt.gamificator.ApiException;
import ch.heigvd.amt.gamificator.ApiResponse;
import ch.heigvd.amt.gamificator.api.dto.BadgeDTO;
import ch.heigvd.amt.gamificator.api.dto.PointScaleDTO;
import ch.heigvd.amt.gamificator.api.dto.AwardPointDTO;
import ch.heigvd.amt.gamificator.api.dto.ActionDTO;
import ch.heigvd.amt.gamificator.api.dto.RuleCreateCommand;
import ch.heigvd.amt.gamificator.api.dto.ConditionDTO;
import io.cucumber.java.en.When;
import lombok.extern.java.Log;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;

import static org.junit.Assert.assertNotNull;

@Log
public class RuleSteps extends Steps {

    List<String> awardBadges;
    List<AwardPointDTO> awardPoints;
    List<RuleCreateCommand> ruleCreateCommands;
    int counter;

    public RuleSteps(Environment environment) {
        super(environment);
    }

    @Given("I GET all point scales and badges created")
    public void iGETAllPointScalesAndBadgesCreated() {
        awardPoints = new ArrayList<>();
        awardBadges = new ArrayList<>();

        try {
            getEnvironment().addSignature("/pointScales");
            ApiResponse apiResponse = getApi().getAllPointScalesWithHttpInfo();
            getEnvironment().processApiResponse(apiResponse);
        } catch (ApiException e) {
            getEnvironment().processApiException(e);
        }

        List<PointScaleDTO> pointScaleDTOAPI = (List<PointScaleDTO>) getEnvironment().getLastApiResponse().getData();

        for (PointScaleDTO pointScaleDTO : pointScaleDTOAPI){
            AwardPointDTO awardPointDTO = new AwardPointDTO();
            awardPointDTO.setPointScaleName(pointScaleDTO.getName());
            awardPointDTO.setValue(10);
            awardPoints.add(awardPointDTO);
        }

        try {
            getEnvironment().addSignature("/badges");
            ApiResponse apiResponse = getApi().getAllbadgesWithHttpInfo();
            getEnvironment().processApiResponse(apiResponse);
        } catch (ApiException e) {
            getEnvironment().processApiException(e);
        }

        List<BadgeDTO> badgeDTOAPI = (List<BadgeDTO>) getEnvironment().getLastApiResponse().getData();

        for (BadgeDTO badgeDTO : badgeDTOAPI){
            awardBadges.add(badgeDTO.getName());
        }
    }

    @And("there is {int} rule payload")
    public void thereIsRulePayload(int nbRules) throws URISyntaxException {
        ruleCreateCommands = new ArrayList<>();
        for (int i = 0; i < nbRules; i++) {
            counter = i;
            RuleCreateCommand ruleCreateCommand = new RuleCreateCommand();

            ConditionDTO conditionDTO = new ConditionDTO();
            conditionDTO.setType("my first post " + counter);
            ruleCreateCommand.setCondition(conditionDTO);

            ActionDTO actionDTO = new ActionDTO();
            actionDTO.setAwardBadges(awardBadges);
            actionDTO.setAwardPoints(awardPoints);
            ruleCreateCommand.setThen(actionDTO);

            ruleCreateCommands.add(ruleCreateCommand);
        }
    }

    @When("I POST the rule payload to the \\/rules endpoint$")
    public void iPOSTTheRulePayloadToTheRulesEndpoint() {
        getEnvironment().addSignature("/rules");

        for (RuleCreateCommand ruleCreateCommand : ruleCreateCommands) {
            try {
                ApiResponse apiResponse = getApi().createRuleWithHttpInfo(ruleCreateCommand);
                getEnvironment().processApiResponse(apiResponse);
            } catch (ApiException e) {
                getEnvironment().processApiException(e);
            }
        }
    }

    @And("I receive the last created rule id")
    public void iReceiveTheLastCreatedRuleId() {
        Long ruleDTOId = (Long) getEnvironment().getLastApiResponse().getData();
        assertNotNull(ruleDTOId);
    }
}
