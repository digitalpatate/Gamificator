package ch.heigvd.amt.gamificator.api.spec.steps;

import ch.heigvd.amt.gamificator.ApiException;
import ch.heigvd.amt.gamificator.ApiResponse;
import ch.heigvd.amt.gamificator.api.dto.BadgeCreateCommand;
import ch.heigvd.amt.gamificator.api.dto.BadgeDTO;
import ch.heigvd.amt.gamificator.api.dto.PointScaleCreateCommand;
import ch.heigvd.amt.gamificator.api.spec.helpers.Environment;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class BadgeSteps extends Steps {

    BadgeCreateCommand badgeCreateCommand;

    public BadgeSteps(Environment environment) {
        super(environment);
    }

    @Given("I POST the badge payload to the /badges endpoints")
    public void iPOSTTheBadgePayloadToTheBadgesEndpoints() throws Throwable {
        try {
            ApiResponse apiResponse =
                    getApi().createBadgeWithHttpInfo(badgeCreateCommand.getName(), badgeCreateCommand.getDescription());
            getEnvironment().processApiResponse(apiResponse);
        } catch (ApiException e) {
            getEnvironment().processApiException(e);
        }
    }

    @Given("there is a badge payload")
    public void thereIsABadgePayload() {
        badgeCreateCommand = new BadgeCreateCommand();
        badgeCreateCommand.setName("SuperMan");
        badgeCreateCommand.setDescription("Badge for super hero only, includes : " +
                "Super Strength, Super Speed, Enhanced Leaping, Super Durability and Super Senses");
    }


}
