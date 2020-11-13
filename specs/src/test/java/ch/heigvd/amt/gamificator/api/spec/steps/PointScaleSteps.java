package ch.heigvd.amt.gamificator.api.spec.steps;

import ch.heigvd.amt.gamificator.ApiException;
import ch.heigvd.amt.gamificator.ApiResponse;
import ch.heigvd.amt.gamificator.api.DefaultApi;
import ch.heigvd.amt.gamificator.api.dto.ApplicationCreateCommand;
import ch.heigvd.amt.gamificator.api.dto.PointScaleCreateCommand;
import ch.heigvd.amt.gamificator.api.dto.PointScaleDTO;
import ch.heigvd.amt.gamificator.api.spec.helpers.Environment;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import java.net.URI;
import java.net.URISyntaxException;
import static org.junit.Assert.assertEquals;

public class PointScaleSteps extends Steps {
    PointScaleCreateCommand pointScaleCreateCommand;

    public PointScaleSteps(Environment environment) {
        super(environment);
    }

    @Given("there is a point scale payload with an application id of {int}")
    public void thereIsAPointScalePayloadWithAnApplicationIdOf(int id) {
        pointScaleCreateCommand = new PointScaleCreateCommand();
        pointScaleCreateCommand.setName("CommunityScore");
        pointScaleCreateCommand.setDescription("Rewards users help to the community");
        pointScaleCreateCommand.setApplicationId(1L);
    }

    @And("there is an application with the id {int}")
    public void thereIsAnApplicationWithTheId(int id) throws URISyntaxException {
        ApplicationCreateCommand applicationCreateCommand = new ApplicationCreateCommand()
                .name("Test app")
                .url(new URI("http://localhost:9090"));

        try {
            ApiResponse apiResponse = getApi().createApplicationWithHttpInfo(applicationCreateCommand);
            getEnvironment().processApiResponse(apiResponse);
        } catch (ApiException e) {
            getEnvironment().processApiException(e);
        }
    }

    @When("I POST the point scale payload to the \\/pointScales endpoint")
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
}
