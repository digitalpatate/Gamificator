package ch.heigvd.amt.gamificator.api.spec.steps;

import ch.heigvd.amt.gamificator.ApiException;
import ch.heigvd.amt.gamificator.ApiResponse;
import ch.heigvd.amt.gamificator.api.dto.CreateEventCommand;
import ch.heigvd.amt.gamificator.api.dto.RuleDTO;
import ch.heigvd.amt.gamificator.api.spec.helpers.Environment;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EventSteps extends Steps {

    List<CreateEventCommand> createEventCommands;
    RuleDTO lastCreatedRule;

    public EventSteps(Environment environment) {
        super(environment);
    }

    @Given("there is {int} event payload")
    public void ThereIsEventPayload(int nbEvents) {
        createEventCommands = new ArrayList<>();
        UUID uuid = UUID.fromString("22d89507-be5b-48fc-8779-" + String.format("%012d", 1));

        for(int event = 0; event < nbEvents; ++event) {
            CreateEventCommand createEventCommand = new CreateEventCommand();
            createEventCommand.setUserUUID(uuid);
            createEventCommand.setType(lastCreatedRule.getCondition().getType());
            createEventCommand.setTimestamp(OffsetDateTime.now());
            createEventCommands.add(createEventCommand);
        }
    }

    @And("I get the last created rule")
    public void IReceiveTheLastCreatedRule(){
        lastCreatedRule = (RuleDTO) getEnvironment().getLastApiResponse().getData();
    }

    @When("I POST the event payload to the /events endpoint$")
    public void iPOSTTheEventPayloadToTheEventsEndpoint() {
        for (CreateEventCommand createEventCommand : createEventCommands) {
            try {
                getEnvironment().addSignature("/events");
                ApiResponse apiResponse = getApi().createEventWithHttpInfo(createEventCommand);
                getEnvironment().processApiResponse(apiResponse);
            } catch (ApiException e) {
                getEnvironment().processApiException(e);
            }
        }
    }
}
