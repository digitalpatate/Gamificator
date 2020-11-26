package ch.heigvd.amt.gamificator.filter;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import javax.servlet.http.HttpServletRequest;

@AllArgsConstructor
public class APIKeyAuthFilter extends AbstractPreAuthenticatedProcessingFilter {

    private String keyHeaderName;
    private String secretHeaderName;

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        String key = request.getHeader(keyHeaderName);
        String secret = request.getHeader(secretHeaderName);
        
        return new String[]{key,secret};
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return "N/A";
    }

}