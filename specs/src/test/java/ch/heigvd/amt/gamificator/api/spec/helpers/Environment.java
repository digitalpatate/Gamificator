package ch.heigvd.amt.gamificator.api.spec.helpers;

import ch.heigvd.amt.gamificator.ApiResponse;
import ch.heigvd.amt.gamificator.api.DefaultApi;
import ch.heigvd.amt.gamificator.ApiException;
import lombok.Getter;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;

public class Environment {

    @Getter private DefaultApi api;
    @Getter private ApiResponse lastApiResponse;
    @Getter private ApiException lastApiException;
    @Getter private boolean lastApiCallThrewException;
    @Getter private int lastStatusCode;
    @Getter private String lastReceivedLocationHeader;

    public Environment() throws IOException {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("environment.properties"));
        String url = properties.getProperty("ch.heigvd.amt.gamificator.server.url");
        api = new DefaultApi();
        api.getApiClient().setBasePath(url);
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public void processApiResponse(ApiResponse apiResponse) {
        lastApiResponse = apiResponse;
        lastApiCallThrewException = false;
        lastApiException = null;
        lastStatusCode = lastApiResponse.getStatusCode();
        List<String> locationHeaderValues = (List<String>)lastApiResponse.getHeaders().get("Location");
        lastReceivedLocationHeader = locationHeaderValues != null ? locationHeaderValues.get(0) : null;
    }

    public void processApiException(ApiException apiException) {
        lastApiCallThrewException = true;
        lastApiResponse = null;
        lastApiException = apiException;
        lastStatusCode = lastApiException.getCode();
    }

    public void reset() {

    }
}
