package ch.heigvd.amt.gamificator.filter;

import lombok.AllArgsConstructor;
import org.hibernate.mapping.Map;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@AllArgsConstructor
public class APIKeyAuthFilter extends AbstractPreAuthenticatedProcessingFilter {

    private String keyHedaerName;

    private String secretHeaderName;




    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        String key = request.getHeader(keyHedaerName);
        String secret = request.getHeader(secretHeaderName);

        return new String[]{key,secret};
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return "N/A";
    }

}