package ch.heigvd.amt.gamificator.filter;

import ch.heigvd.amt.gamificator.api.application.ApplicationService;
import ch.heigvd.amt.gamificator.exceptions.ApiException;
import lombok.extern.java.Log;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Log
public class ApiKeyFilter extends GenericFilterBean {
    private ApplicationService applicationService;

    public ApiKeyFilter(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        List<String> filter = getFilter();

        if ((req.getRequestURI().startsWith("/applications") && req.getMethod().startsWith("POST")) || !filter.contains(req.getRequestURI())) {
            log.info("filter in");
            filterChain.doFilter(servletRequest, servletResponse);
            log.info("filter out");
            return;
        }

        String apiKey = req.getHeader("X-API-KEY");
        String secret = req.getHeader("X-API-SECRET");
        log.info("API key: " + apiKey);

        long applicationId;
        try {
            applicationId = this.applicationService.hasGoodCredential(apiKey, secret);
        } catch (ApiException e) {
            log.info(e.getMessage());

            HttpServletResponse res = (HttpServletResponse) servletResponse;
            res.reset();
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            return;
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(apiKey, secret);
        usernamePasswordAuthenticationToken.setDetails(applicationId);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private List<String> getFilter() {
        List<String> filter = new ArrayList<>();
        filter.add("/badges");
        filter.add("/users");
        filter.add("/leaderboards");
        filter.add("/rules");
        filter.add("/pointScales");
        filter.add("/events");

        return filter;
    }
}
