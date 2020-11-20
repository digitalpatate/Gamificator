package ch.heigvd.amt.gamificator.api.spec.steps;

import ch.heigvd.amt.gamificator.ApiResponse;
import ch.heigvd.amt.gamificator.api.dto.PointScaleCreateCommand;
import ch.heigvd.amt.gamificator.api.dto.PointScaleDTO;
import ch.heigvd.amt.gamificator.api.spec.helpers.Environment;
import ch.heigvd.amt.gamificator.ApiException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import java.util.List;

import static org.junit.Assert.*;

public class PointScaleSteps extends Steps {
    PointScaleCreateCommand pointScaleCreateCommand;

    public PointScaleSteps(Environment environment) {
        super(environment);
    }

    @Given("there is a point scale payload with an application id of {long}")
    public void thereIsAPointScalePayloadWithAnApplicationIdOf(long id) {
        pointScaleCreateCommand = new PointScaleCreateCommand();
        pointScaleCreateCommand.setName("CommunityScore");
        pointScaleCreateCommand.setDescription("Rewards users help to the community");
        pointScaleCreateCommand.setApplicationId(id);
    }

    @When("I POST the point scale payload to the /pointScales endpoint$")
    public void iPOSTThePointScalePayloadToThePointScalesEndpoint() {
        try {
            ApiResponse apiResponse = getApi().createPointScaleWithHttpInfo(pointScaleCreateCommand);
            getEnvironment().processApiResponse(apiResponse);
        } catch (ApiException e) {
            getEnvironment().processApiException(e);
        }
    }

    @And("I receive the created point scale")
    public void iReceiveTheCreatedPointScale() {
        PointScaleDTO pointScaleDTO = (PointScaleDTO) getEnvironment().getLastApiResponse().getData();
        assertEquals(pointScaleDTO.getName(), pointScaleCreateCommand.getName());
        assertEquals(pointScaleDTO.getDescription(), pointScaleCreateCommand.getDescription());
        assertEquals(pointScaleDTO.getApplicationId(), pointScaleCreateCommand.getApplicationId());
    }

    @When("I GET the point scale with the id {long}")
    public void iGETThePointScaleWithTheId(long id) {
        try {
            ApiResponse apiResponse = getApi().getPointScaleWithHttpInfo(id);
            getEnvironment().processApiResponse(apiResponse);
        } catch (ApiException e) {
            getEnvironment().processApiException(e);
        }
    }

    @When("a DELETE is sent to the pointscales endpoint with the id {long}")
    public void aDELETEIsSentToThePointscalesEndpointWithTheId(long id) {
        try {
            ApiResponse apiResponse = getApi().deletePointScaleWithHttpInfo(id);
            getEnvironment().processApiResponse(apiResponse);
        } catch (ApiException e) {
            getEnvironment().processApiException(e);
        }
    }

    @And("I don't receive a point scale")
    public void iDonTReceiveAPointScale() {
        Object data = getEnvironment().getLastApiResponse();
        assertNull(data);
    }

    @When("I send a GET to the pointscales endpoint")
    public void iSendAGETToThePointscalesEndpoint() {
        try {
            ApiResponse apiResponse = getApi().getAllPointScalesWithHttpInfo(null);
            getEnvironment().processApiResponse(apiResponse);
        } catch (ApiException e) {
            getEnvironment().processApiException(e);
        }

    }

    @And("I receive {int} pointscales with differents id")
    public void iReceiveTwoPointscalesWithDifferentsId(int nbPointScale) {
        List<PointScaleDTO> pointScaleDTOList = (List<PointScaleDTO>) getEnvironment().getLastApiResponse().getData();
        assertEquals(nbPointScale, pointScaleDTOList.size());
        assertNotNull(pointScaleDTOList.get(0));
        assertNotNull(pointScaleDTOList.get(1));
        assertNotEquals(pointScaleDTOList.get(0).getId(), pointScaleDTOList.get(1).getId());
    }

    @When("I send a GET to the pointscales endpoint with an application id of {long}")
    public void iSendAGETToThePointscalesEndpointWithAnApplicationIdOf(long id) {
        try {
            ApiResponse apiResponse = getApi().getAllPointScalesWithHttpInfo(id);
            getEnvironment().processApiResponse(apiResponse);
        } catch (ApiException e) {
            getEnvironment().processApiException(e);
        }
    }
}
