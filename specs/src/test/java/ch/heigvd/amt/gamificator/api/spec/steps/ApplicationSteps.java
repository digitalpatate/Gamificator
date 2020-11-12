package ch.heigvd.amt.gamificator.api.spec.steps;

import ch.heigvd.amt.gamificator.ApiException;
import ch.heigvd.amt.gamificator.ApiResponse;
import ch.heigvd.amt.gamificator.api.DefaultApi;
import ch.heigvd.amt.gamificator.api.dto.ApplicationCreateCommand;
import ch.heigvd.amt.gamificator.api.dto.ApplicationCreateDTO;
import ch.heigvd.amt.gamificator.api.spec.helpers.Environment;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.net.URI;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ApplicationSteps {

    private Environment environment;
    private DefaultApi api;

    ApplicationCreateCommand applicationCreateCommand;

    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private int lastStatusCode;

    ApplicationCreateDTO applicationCreateDTO;

    private String lastReceivedLocationHeader;

    public ApplicationSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();
    }

    @Given("there is a Gamificator server")
    public void there_is_a_Gamificator_server() throws Throwable {
        assertNotNull(api);
    }

    @Given("I have a application payload")
    public void i_have_a_application_payload() throws Throwable {
        applicationCreateCommand = new ch.heigvd.amt.gamificator.api.dto.ApplicationCreateCommand()
          .name("Test app")
          .url(new URI("http://localhost:9090"));
    }

    @When("^I POST the application payload to the /applications endpoint$")
    public void i_POST_the_application_payload_to_the_fruits_endpoint() throws Throwable {
        try {
            lastApiResponse = api.createApplicationWithHttpInfo(applicationCreateCommand);
            processApiResponse(lastApiResponse);
        } catch (ApiException e) {
            processApiException(e);
        }
    }

    @Then("I receive a {int} status code")
    public void i_receive_a_status_code(int expectedStatusCode) throws Throwable {
        assertEquals(expectedStatusCode, lastStatusCode);
    }

    @When("^I send a GET to the /applications endpoint$")
    public void iSendAGETToTheApplicationEndpoint() {
        try {
            lastApiResponse = api.getAllApplicationWithHttpInfo();
            processApiResponse(lastApiResponse);
        } catch (ApiException e) {
            processApiException(e);
        }
    }

    @Then("I receive a {int} status code with a location header")
    public void iReceiveAStatusCodeWithALocationHeader(int arg0) {
    }

    @When("I send a GET to the URL in the location header")
    public void iSendAGETToTheURLInTheLocationHeader() {
        Long id = Long.valueOf(lastReceivedLocationHeader.substring(lastReceivedLocationHeader.lastIndexOf('/') + 1));
        try {
            lastApiResponse = api.getApplicationWithHttpInfo(id);
            processApiResponse(lastApiResponse);
            applicationCreateDTO = (ApplicationCreateDTO)lastApiResponse.getData();
        } catch (ApiException e) {
            processApiException(e);
        }
    }

    @And("I receive a payload that is the same as the application payload")
    public void iReceiveAPayloadThatIsTheSameAsTheApplicationPayload() {
        assertEquals(applicationCreateCommand, applicationCreateDTO);
    }

    private void processApiResponse(ApiResponse apiResponse) {
        lastApiResponse = apiResponse;
        lastApiCallThrewException = false;
        lastApiException = null;
        lastStatusCode = lastApiResponse.getStatusCode();
        List<String> locationHeaderValues = (List<String>)lastApiResponse.getHeaders().get("Location");
        lastReceivedLocationHeader = locationHeaderValues != null ? locationHeaderValues.get(0) : null;
    }

    private void processApiException(ApiException apiException) {
        lastApiCallThrewException = true;
        lastApiResponse = null;
        lastApiException = apiException;
        lastStatusCode = lastApiException.getCode();
    }

}
