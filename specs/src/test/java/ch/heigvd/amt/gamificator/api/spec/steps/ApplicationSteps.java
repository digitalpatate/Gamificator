package ch.heigvd.amt.gamificator.api.spec.steps;

import ch.heigvd.amt.gamificator.ApiException;
import ch.heigvd.amt.gamificator.ApiResponse;
import ch.heigvd.amt.gamificator.api.dto.ApplicationCreateCommand;
import ch.heigvd.amt.gamificator.api.dto.ApplicationCreateDTO;
import ch.heigvd.amt.gamificator.api.spec.helpers.Environment;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;

public class ApplicationSteps extends Steps {
    ApplicationCreateCommand applicationCreateCommand;
    ApplicationCreateDTO applicationCreateDTO;

    public ApplicationSteps(Environment environment) {
        super(environment);
    }

    @Given("I have a application payload")
    public void i_have_a_application_payload() throws Throwable {
        applicationCreateCommand = new ch.heigvd.amt.gamificator.api.dto.ApplicationCreateCommand()
                .name("Test app")
                .url(new URI("http://localhost:9090"));
    }

    @When("^I POST the application payload to the /applications endpoint$")
    public void i_POST_the_application_payload_to_the_applications_endpoint() throws Throwable {
        try {
            ApiResponse apiResponse = getApi().createApplicationWithHttpInfo(applicationCreateCommand);
            getEnvironment().processApiResponse(apiResponse);
        } catch (ApiException e) {
            getEnvironment().processApiException(e);
        }
    }

    @When("^I send a GET to the /applications endpoint$")
    public void iSendAGETToTheApplicationEndpoint() {
        try {
            getEnvironment().addSignature("/applications");
            ApiResponse apiResponse = getApi().getApplicationWithHttpInfo();
            getEnvironment().processApiResponse(apiResponse);
        } catch (ApiException e) {
            getEnvironment().processApiException(e);
        }
    }

    @When("I send a GET to the URL in the location header")
    public void iSendAGETToTheURLInTheLocationHeader() {
        try {
            getEnvironment().addSignature("/applications");
            ApiResponse apiResponse = getApi().getApplicationWithHttpInfo();
            getEnvironment().processApiResponse(apiResponse);
            applicationCreateDTO = (ApplicationCreateDTO) getEnvironment().getLastApiResponse().getData();
        } catch (ApiException e) {
            getEnvironment().processApiException(e);
        }
    }

    @And("I receive a payload that is the same as the application payload")
    public void iReceiveAPayloadThatIsTheSameAsTheApplicationPayload() {
        assertEquals(applicationCreateCommand, applicationCreateDTO);
    }

    @And("I PUT the last created application payload to the /applications endpoint$")
    public void iPUTTheLastCreatedApplicationPayloadToTheApplicationsEndpoint() {
        try {
            ApplicationCreateDTO applicationCreateDTO = (ApplicationCreateDTO) getEnvironment().getLastApiResponse().getData();
            getEnvironment().setApiKey(applicationCreateDTO.getKey());
            getEnvironment().setApiSecret(applicationCreateDTO.getSecret());
            getEnvironment().getApi().getApiClient().addDefaultHeader("x-api-key", getEnvironment().getApiKey());
            getEnvironment().addSignature(String.format("/applications/%d", applicationCreateDTO.getId()));

            ApiResponse apiResponse = getApi().updateApplicationWithHttpInfo((long)applicationCreateDTO.getId(), applicationCreateCommand);
            getEnvironment().processApiResponse(apiResponse);
        } catch (ApiException e) {
            getEnvironment().processApiException(e);
        }
    }
}
