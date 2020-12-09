package ch.heigvd.amt.gamificator.api.spec.steps;

import ch.heigvd.amt.gamificator.ApiResponse;
import ch.heigvd.amt.gamificator.api.dto.PointScaleCreateCommand;
import ch.heigvd.amt.gamificator.api.dto.PointScaleDTO;
import ch.heigvd.amt.gamificator.api.spec.helpers.Environment;
import ch.heigvd.amt.gamificator.ApiException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PointScaleSteps extends Steps {

    List<PointScaleCreateCommand> pointScaleCreateCommands;

    public PointScaleSteps(Environment environment) {
        super(environment);
    }

    @When("I POST the point scale payload to the /pointScales endpoint$")
    public void iPOSTThePointScalePayloadToThePointScalesEndpoint() {
        for (PointScaleCreateCommand pointScaleCreateCommand: pointScaleCreateCommands) {
            try {
                ApiResponse apiResponse = getApi().createPointScaleWithHttpInfo(pointScaleCreateCommand);
                getEnvironment().processApiResponse(apiResponse);
            } catch (ApiException e) {
                getEnvironment().processApiException(e);
            }
        }
    }

    @And("I receive the created point scale")
    public void iReceiveTheCreatedPointScale() {
        PointScaleDTO pointScaleDTO = (PointScaleDTO) getEnvironment().getLastApiResponse().getData();
        assertEquals(pointScaleDTO.getName(), pointScaleCreateCommands.get(pointScaleCreateCommands.size() - 1).getName());
        assertEquals(pointScaleDTO.getDescription(), pointScaleCreateCommands.get(pointScaleCreateCommands.size() - 1).getDescription());
    }

    @And("I don't receive a point scale")
    public void iDonTReceiveAPointScale() {
        Object data = getEnvironment().getLastApiResponse().getData();
        assertNull(data);
    }

    @When("I send a GET to the point scales endpoint")
    public void iSendAGETToThePointscalesEndpoint() {
        try {
            ApiResponse apiResponse = getApi().getAllPointScalesWithHttpInfo();
            getEnvironment().processApiResponse(apiResponse);
        } catch (ApiException e) {
            getEnvironment().processApiException(e);
        }

    }

    @And("I receive {int} point scales with different id")
    public void iReceivePointScalesWithDifferentId(int nbPointScales) {
        List<PointScaleDTO> pointScaleDTOList = (List<PointScaleDTO>) getEnvironment().getLastApiResponse().getData();
        assertEquals(nbPointScales, pointScaleDTOList.size());
        for (int i = 0; i < nbPointScales; i++) {
            for (int j = i + 1; j < nbPointScales; j++) {
                assertNotEquals(pointScaleDTOList.get(i).getId(), pointScaleDTOList.get(j).getId());
            }
        }
    }

    @Given("there is {int} point scale payload")
    public void thereIsPointScalePayload(int nbPointScales) {
        pointScaleCreateCommands = new ArrayList<>();
        for (int i = 0; i < nbPointScales; i++) {
            PointScaleCreateCommand pointScaleCreateCommand = new PointScaleCreateCommand();
            pointScaleCreateCommand.setName("CommunityScore " + i);
            pointScaleCreateCommand.setDescription("Rewards users help to the community");
            pointScaleCreateCommands.add(pointScaleCreateCommand);
        }
    }

    @When("I GET a previously created point scale with his id")
    public void iGETAPreviouslyCreatedPointScaleWithHisId() {
        try {
            ApiResponse apiResponse = getApi().getPointScaleWithHttpInfo(((PointScaleDTO) getEnvironment().getLastApiResponse().getData()).getId());
            getEnvironment().processApiResponse(apiResponse);
        } catch (ApiException e) {
            getEnvironment().processApiException(e);
        }
    }
}
