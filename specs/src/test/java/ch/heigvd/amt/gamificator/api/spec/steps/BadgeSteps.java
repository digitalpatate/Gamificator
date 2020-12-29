package ch.heigvd.amt.gamificator.api.spec.steps;

import ch.heigvd.amt.gamificator.ApiException;
import ch.heigvd.amt.gamificator.ApiResponse;
import ch.heigvd.amt.gamificator.api.dto.BadgeCreateCommand;
import ch.heigvd.amt.gamificator.api.dto.BadgeDTO;
import ch.heigvd.amt.gamificator.api.spec.helpers.Environment;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import lombok.extern.java.Log;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@Log
public class BadgeSteps extends Steps {

    List<BadgeCreateCommand> badgeCreateCommands;
    int counter;

    public BadgeSteps(Environment environment) {
        super(environment);
    }

    @And("I POST the badge payload to the /badges endpoints$")
    public void iPOSTTheBadgePayloadToTheBadgesEndpoints() {
        getEnvironment().addSignature("/badges");

        for (BadgeCreateCommand badgeCreateCommand : badgeCreateCommands) {
            try {
                ApiResponse apiResponse =
                        getApi().createBadgeWithHttpInfo(badgeCreateCommand);
                getEnvironment().processApiResponse(apiResponse);
            } catch (ApiException e) {
                getEnvironment().processApiException(e);
            }
        }
    }

    @Given("there is {int} badge payload")
    public void thereIsBadgePayload(int nbBadges) throws URISyntaxException {
        badgeCreateCommands = new ArrayList<>();
        for (int i = 0; i < nbBadges; i++) {
            counter = i;
            BadgeCreateCommand badgeCreateCommand = new BadgeCreateCommand();
            badgeCreateCommand.setName("batman " + counter);
            badgeCreateCommand.setImageUrl(new URI("https://external-content.duckduckgo.com/iu/" +
                    "?u=https%3A%2F%2Ftse4.mm.bing.net%2Fth%3Fid%3DOIP.5PHxSPT-OxxSWoYBFuD-GAHaLV%26pid%3DApi&f=1"));
            badgeCreateCommands.add(badgeCreateCommand);
        }
    }

    @And("I receive the created badge")
    public void iReceiveTheCreatedBadge() {
        BadgeDTO badgeDTO = (BadgeDTO) getEnvironment().getLastApiResponse().getData();
        assertEquals(badgeDTO.getName(), badgeCreateCommands.get(badgeCreateCommands.size() - 1).getName());
        assertEquals(badgeDTO.getImageUrl(), badgeCreateCommands.get(badgeCreateCommands.size() - 1).getImageUrl());
    }

    @When("I GET the badge previously created")
    public void iGETTheBadgePreviouslyCreated() {
        try {
            BadgeDTO badgeDTO = ((BadgeDTO) getEnvironment().getLastApiResponse().getData());
            long id = badgeDTO.getId();
            getEnvironment().addSignature(String.format("/badges/%d",id));

            ApiResponse apiResponse = getApi().getBadgeWithHttpInfo(id);
            getEnvironment().processApiResponse(apiResponse);
        } catch (ApiException e) {
            getEnvironment().processApiException(e);
        }
    }

    @And("I don't receive a badge")
    public void iDonTReceiveABadge() {
        Object data = getEnvironment().getLastApiResponse().getData();
        assertNull(data);
    }

    @When("I send a GET to the badge endpoint")
    public void iSendAGETToTheBadgeEndpoint() {
        try {
            getEnvironment().addSignature("/badges");
            ApiResponse apiResponse = getApi().getAllbadgesWithHttpInfo();
            getEnvironment().processApiResponse(apiResponse);
        } catch (ApiException e) {
            getEnvironment().processApiException(e);
        }
    }

    @And("I receive {int} badges with different id")
    public void iReceiveTwoBadgesWithDifferentId(int nbBadges) {
        List<BadgeDTO> badgeDTOList = (List<BadgeDTO>) getEnvironment().getLastApiResponse().getData();
        assertEquals(nbBadges, badgeDTOList.size());
        for (int i = 0; i < nbBadges; i++) {
            for (int j = i + 1; j < nbBadges; j++) {
                assertNotEquals(badgeDTOList.get(i).getId(), badgeDTOList.get(j).getId());
            }
        }
    }

    @And("I receive the updated badge")
    public void iReceiveTheUpdatedBadge() {
        BadgeDTO badgeDTO = (BadgeDTO) getEnvironment().getLastApiResponse().getData();
        assertEquals(badgeDTO.getName(), badgeCreateCommands.get(badgeCreateCommands.size() - 1).getName());
        assertEquals(badgeDTO.getImageUrl(), badgeCreateCommands.get(badgeCreateCommands.size() - 1).getImageUrl());
    }

    @And("I PUT the last created badge payload to the /badges endpoints$")
    public void iPUTTheLastCreatedBadgePayloadToTheBadgesEndpoints() {
        try {
            BadgeDTO badgeDTO = new BadgeDTO();
            badgeDTO.setName(badgeCreateCommands.get(badgeCreateCommands.size() - 1).getName());
            badgeDTO.setImageUrl(badgeCreateCommands.get(badgeCreateCommands.size() - 1).getImageUrl());
            long badgeId = ((BadgeDTO) getEnvironment().getLastApiResponse().getData()).getId();
            getEnvironment().addSignature(String.format("/badges/%d",badgeId));

            ApiResponse apiResponse =
                    getApi().updateBadgeWithHttpInfo(badgeId, badgeDTO);
            getEnvironment().processApiResponse(apiResponse);
        } catch (ApiException e) {
            getEnvironment().processApiException(e);
        }
    }
}