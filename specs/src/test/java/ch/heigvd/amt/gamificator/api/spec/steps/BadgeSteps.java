package ch.heigvd.amt.gamificator.api.spec.steps;

import ch.heigvd.amt.gamificator.ApiException;
import ch.heigvd.amt.gamificator.ApiResponse;
import ch.heigvd.amt.gamificator.api.dto.BadgeCreateCommand;
import ch.heigvd.amt.gamificator.api.dto.BadgeDTO;
import ch.heigvd.amt.gamificator.api.spec.helpers.Environment;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.Assert.*;

public class BadgeSteps extends Steps {

    BadgeCreateCommand badgeCreateCommand_1;
    BadgeCreateCommand badgeCreateCommand_2;

    public BadgeSteps(Environment environment) {
        super(environment);
    }

    @Given("I POST the badge payload one to the /badges endpoints")
    public void iPOSTTheBadgePayloadOneToTheBadgesEndpoints() throws Throwable {
        try {
            ApiResponse apiResponse =
                    getApi().createBadgeWithHttpInfo(badgeCreateCommand_1);
            getEnvironment().processApiResponse(apiResponse);
        } catch (ApiException e) {
            getEnvironment().processApiException(e);
        }
    }

    @Given("I POST the badge payload two to the /badges endpoints")
    public void iPOSTTheBadgePayloadTwoToTheBadgesEndpoints() throws Throwable {
        try {
            ApiResponse apiResponse =
                    getApi().createBadgeWithHttpInfo(badgeCreateCommand_2);
            getEnvironment().processApiResponse(apiResponse);
        } catch (ApiException e) {
            getEnvironment().processApiException(e);
        }
    }

    @Given("there is a badge payload")
    public void thereIsABadgePayload() throws URISyntaxException {
        badgeCreateCommand_1 = new BadgeCreateCommand();
        badgeCreateCommand_1.setName("SuperMan");
        badgeCreateCommand_1.setImageUrl(new URI("https://external-content.duckduckgo.com" +
                "/iu/?u=https%3A%2F%2Fwww.zsl.org%2Fsites%2Fdefault%2Ffiles%2Fmedia%2F2017-12" +
                "%2FFoage%2520male%2520badger.jpg&f=1&nofb=1"));
    }

    @Given("there is a second badge payload")
    public void thereIsASecondBadgePayload() throws URISyntaxException {
        badgeCreateCommand_2 = new BadgeCreateCommand();
        badgeCreateCommand_2.setName("Batman");
        badgeCreateCommand_2.setImageUrl(new URI("https://external-content.duckduckgo.com/iu/" +
                "?u=https%3A%2F%2Ftse4.mm.bing.net%2Fth%3Fid%3DOIP.5PHxSPT-OxxSWoYBFuD-GAHaLV%26pid%3DApi&f=1"));
    }

    @And("I receive the created badge")
    public void iReceiveTheCreatedBadge() {
        BadgeDTO badgeDTO = (BadgeDTO) getEnvironment().getLastApiResponse().getData();
        assertEquals(badgeDTO.getName(), badgeCreateCommand_1.getName());
        assertEquals(badgeDTO.getImageUrl(), badgeCreateCommand_1.getImageUrl());
    }

    @When("I GET the badge with the id {long}")
    public void iGETTheBadgeWithTheId(long id) {
        try {
            ApiResponse apiResponse = getApi().getBadgeWithHttpInfo(id);
            getEnvironment().processApiResponse(apiResponse);
        } catch (ApiException e) {
            getEnvironment().processApiException(e);
        }
    }

    @And("I don't receive a badge")
    public void iDonTReceiveABadge() {
        Object data = getEnvironment().getLastApiResponse();
        assertNull(data);
    }


    @When("I send a GET to the badge endpoint")
    public void iSendAGETToTheBadgeEndpoint() {
        try {
            ApiResponse apiResponse = getApi().getAllbadgesWithHttpInfo();
            getEnvironment().processApiResponse(apiResponse);
        } catch (ApiException e) {
            getEnvironment().processApiException(e);
        }
    }

    @And("I receive {int} badges with different id")
    public void iReceiveTwoBadgesWithDifferentId(int nbBadge) {
        List<BadgeDTO> badgeDTOList = (List<BadgeDTO>) getEnvironment().getLastApiResponse().getData();
        assertEquals(nbBadge, badgeDTOList.size());
        assertNotNull(badgeDTOList.get(0));
        assertNotNull(badgeDTOList.get(1));
        assertNotEquals(badgeDTOList.get(0).getId(), badgeDTOList.get(1).getId());
    }


    @And("I receive the updated badge")
    public void iReceiveTheUpdatedBadge() {
        BadgeDTO badgeDTO = (BadgeDTO) getEnvironment().getLastApiResponse().getData();
        assertEquals(badgeDTO.getName(), badgeCreateCommand_2.getName());
        assertEquals(badgeDTO.getImageUrl(), badgeCreateCommand_2.getImageUrl());
    }

    @Given("I PUT the second badge payload to the /badges endpoints")
    public void iPUTTheSecondBadgePayloadToTheBadgesEndpoints() {
        try {
            ApiResponse apiResponse =
                    getApi().updateBadgeWithHttpInfo(5L, badgeCreateCommand_2);
            getEnvironment().processApiResponse(apiResponse);
        } catch (ApiException e) {
            getEnvironment().processApiException(e);
        }
    }
}