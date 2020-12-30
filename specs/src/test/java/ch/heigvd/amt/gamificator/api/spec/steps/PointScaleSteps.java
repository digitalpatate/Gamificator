package ch.heigvd.amt.gamificator.api.spec.steps;

import ch.heigvd.amt.gamificator.ApiResponse;
import ch.heigvd.amt.gamificator.api.dto.BadgeDTO;
import ch.heigvd.amt.gamificator.api.dto.PointScaleCreateCommand;
import ch.heigvd.amt.gamificator.api.dto.PointScaleDTO;
import ch.heigvd.amt.gamificator.api.spec.helpers.Environment;
import ch.heigvd.amt.gamificator.ApiException;
import ch.heigvd.amt.gamificator.api.spec.helpers.Signature;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
@Log
public class PointScaleSteps extends Steps {

    List<PointScaleCreateCommand> pointScaleCreateCommands;
    PointScaleDTO lastCreatedPointScale;
    int counter = 0;

    public PointScaleSteps(Environment environment) {
        super(environment);
    }

    @When("I POST the point scale payload to the /pointScales endpoint$")
    public void iPOSTThePointScalePayloadToThePointScalesEndpoint() {
        getEnvironment().addSignature("/pointScales");
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
        lastCreatedPointScale = (PointScaleDTO) getEnvironment().getLastApiResponse().getData();
        assertEquals(lastCreatedPointScale.getName(), pointScaleCreateCommands.get(pointScaleCreateCommands.size() - 1).getName());
        assertEquals(lastCreatedPointScale.getDescription(), pointScaleCreateCommands.get(pointScaleCreateCommands.size() - 1).getDescription());
    }

    @And("I don't receive a point scale")
    public void iDonTReceiveAPointScale() {
        Object data = getEnvironment().getLastApiResponse().getData();
        assertNull(data);
    }

    @When("I send a GET to the point scales endpoint")
    public void iSendAGETToThePointscalesEndpoint() {
        try {
            getEnvironment().addSignature("/pointScales");
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
            counter++;
            PointScaleCreateCommand pointScaleCreateCommand = new PointScaleCreateCommand();
            pointScaleCreateCommand.setName("CommunityScore" + counter);
            pointScaleCreateCommand.setDescription("Rewards users help to the community");
            pointScaleCreateCommands.add(pointScaleCreateCommand);
        }
    }

    @When("I GET a previously created point scale with his id")
    public void iGETAPreviouslyCreatedPointScaleWithHisId() {
        try {
            long id = ((PointScaleDTO) getEnvironment().getLastApiResponse().getData()).getId();
            getEnvironment().addSignature(String.format("/pointScales/%d", id));
            ApiResponse apiResponse = getApi().getPointScaleWithHttpInfo(id);
            getEnvironment().processApiResponse(apiResponse);
        } catch (ApiException e) {
            getEnvironment().processApiException(e);
        }
    }

    @And("I PUT the last created point scale payload to the /pointScales endpoint$")
    public void iPUTTheLastCreatedPointScalePayloadToThePointScalesEndpoint() {
        try {
            PointScaleCreateCommand pointScaleCreateCommand = pointScaleCreateCommands.get(pointScaleCreateCommands.size() - 1);
            long pointScaleId = lastCreatedPointScale.getId();
            getEnvironment().addSignature(String.format("/pointScales/%d", pointScaleId));

            ApiResponse apiResponse =
                    getApi().updatePointScaleWithHttpInfo(pointScaleId, pointScaleCreateCommand);
            getEnvironment().processApiResponse(apiResponse);
        } catch (ApiException e) {
            getEnvironment().processApiException(e);
        }
    }

    @And("I receive the updated point scale")
    public void iReceiveTheUpdatedPointScale() {
        PointScaleDTO pointScaleDTO = ((PointScaleDTO) getEnvironment().getLastApiResponse().getData());
        PointScaleCreateCommand pointScaleCreateCommand = pointScaleCreateCommands.get(pointScaleCreateCommands.size() - 1);
        assertEquals(pointScaleCreateCommand.getName(), pointScaleDTO.getName());
        assertEquals(pointScaleCreateCommand.getDescription(), pointScaleDTO.getDescription());
    }
}
