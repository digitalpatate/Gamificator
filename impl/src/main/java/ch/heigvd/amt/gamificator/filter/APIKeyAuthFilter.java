package ch.heigvd.amt.gamificator.filter;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import javax.servlet.http.HttpServletRequest;

@AllArgsConstructor
@Log
public class APIKeyAuthFilter extends AbstractPreAuthenticatedProcessingFilter {

    private String keyHeaderName;
    private String signatureHeaderName;

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {


        String key = request.getHeader(keyHeaderName);
        String url = request.getRequestURL().toString();
        String signature = request.getHeader(signatureHeaderName);
        
        return new String[]{key,url,signature};
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return "N/A";
    }

}