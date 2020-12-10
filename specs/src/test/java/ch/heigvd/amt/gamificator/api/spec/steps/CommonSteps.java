package ch.heigvd.amt.gamificator.api.spec.steps;

import ch.heigvd.amt.gamificator.api.dto.ApplicationCreateDTO;
import ch.heigvd.amt.gamificator.api.spec.helpers.Environment;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CommonSteps extends Steps {

    public CommonSteps(Environment environment) {
        super(environment);
    }

    @Given("there is a Gamificator server")
    public void there_is_a_Gamificator_server() throws Throwable {
        assertNotNull(getApi());
    }

    @Then("I receive a {int} status code")
    public void i_receive_a_status_code(int expectedStatusCode) throws Throwable {
        assertEquals(expectedStatusCode, getEnvironment().getLastStatusCode());
    }

    @Then("the I have an API key")
    public void theIHaveAnAPIKey() {
        ApplicationCreateDTO applicationCreateDTO = (ApplicationCreateDTO) getEnvironment().getLastApiResponse().getData();
        getEnvironment().setApiKey(applicationCreateDTO.getKey());
        getEnvironment().setApiSecret(applicationCreateDTO.getSecret());
        getEnvironment().getApi().getApiClient().addDefaultHeader("x-api-key", getEnvironment().getApiKey());
        //getEnvironment().getApi().getApiClient().addDefaultHeader("X-API-SECRET", getEnvironment().getApiSecret());
    }
}
