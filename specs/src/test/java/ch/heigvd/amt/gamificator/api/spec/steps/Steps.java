package ch.heigvd.amt.gamificator.api.spec.steps;

import ch.heigvd.amt.gamificator.api.DefaultApi;
import ch.heigvd.amt.gamificator.api.spec.helpers.Environment;
import lombok.Getter;

public class Steps {
    @Getter private Environment environment;
    @Getter private DefaultApi api;

    public Steps(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();
    }
}
