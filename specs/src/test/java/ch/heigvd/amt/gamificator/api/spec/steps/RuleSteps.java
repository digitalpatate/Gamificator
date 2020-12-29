package ch.heigvd.amt.gamificator.api.spec.steps;

import ch.heigvd.amt.gamificator.api.dto.*;
import ch.heigvd.amt.gamificator.api.dto.ActionDTO;
import ch.heigvd.amt.gamificator.api.dto.AwardPointDTO;
import ch.heigvd.amt.gamificator.api.dto.BadgeDTO;
import ch.heigvd.amt.gamificator.api.dto.ConditionDTO;
import ch.heigvd.amt.gamificator.api.dto.PointScaleDTO;
import ch.heigvd.amt.gamificator.api.dto.RuleCreateCommand;
import ch.heigvd.amt.gamificator.api.dto.RuleDTO;
import ch.heigvd.amt.gamificator.api.spec.helpers.Environment;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import ch.heigvd.amt.gamificator.ApiException;
import ch.heigvd.amt.gamificator.ApiResponse;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.java.Log;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Log
public class RuleSteps extends Steps {

    List<String> awardBadges;
    List<AwardPointDTO> awardPoints;
    List<RuleCreateCommand> ruleCreateCommands;
    RuleDTO lastCreatedRule;
    int counter = 0;

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
    public void thereIsRulePayload(int nbRules) {
        ruleCreateCommands = new ArrayList<>();
        for (int i = 0; i < nbRules; i++) {
            counter++;
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

    @When("I POST the rule payload to the /rules endpoint$")
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

    @And("I receive the last created rule")
    public void iReceiveTheLastCreatedRule() {
        lastCreatedRule = (RuleDTO) getEnvironment().getLastApiResponse().getData();
        assertNotNull(lastCreatedRule);
    }

    @And("I PUT the last created rule payload to the /rules endpoint$")
    public void iPUTTheLastCreatedRulePayloadToTheRulesEndpoint() {
        try {
            RuleUpdateCommand ruleUpdateCommand = new RuleUpdateCommand();
            ruleUpdateCommand.setCondition(ruleCreateCommands.get(0).getCondition());
            ruleUpdateCommand.setThen(ruleCreateCommands.get(0).getThen());
            getEnvironment().addSignature(String.format("/rules/%d", lastCreatedRule.getId()));

            ApiResponse apiResponse =
                    getApi().updateRuleWithHttpInfo(lastCreatedRule.getId(), ruleUpdateCommand);
            getEnvironment().processApiResponse(apiResponse);
        } catch (ApiException e) {
            getEnvironment().processApiException(e);
        }
    }

    @When("I GET all rules")
    public void iGETAllRules() {
        try {
            getEnvironment().addSignature("/rules");

            ApiResponse apiResponse =
                    getApi().getAllRulesWithHttpInfo();
            getEnvironment().processApiResponse(apiResponse);
        } catch (ApiException e) {
            getEnvironment().processApiException(e);
        }
    }

    @Then("I receive {int} rules")
    public void iReceiveRules(int nbRules) {
        List<RuleDTO> ruleDTOS = (List<RuleDTO>) getEnvironment().getLastApiResponse().getData();
        assertEquals(nbRules, ruleDTOS.size());
    }

    @When("I DELETE the previously created rule")
    public void iDELETEThePreviouslyCreatedRule() {
        try {
            long id = ((RuleDTO) getEnvironment().getLastApiResponse().getData()).getId();
            getEnvironment().addSignature(String.format("/rules/%d", id));

            ApiResponse apiResponse =
                    getApi().deleteRuleWithHttpInfo(id);
            getEnvironment().processApiResponse(apiResponse);
        } catch (ApiException e) {
            getEnvironment().processApiException(e);
        }
    }
}
