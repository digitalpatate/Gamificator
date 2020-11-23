package ch.heigvd.amt.gamificator.filter;

import ch.heigvd.amt.gamificator.api.application.ApplicationService;
import ch.heigvd.amt.gamificator.api.model.ApplicationDTO;
import ch.heigvd.amt.gamificator.exceptions.NotFoundException;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

        if (!req.getRequestURI().startsWith("/")) {
            filterChain.doFilter(servletRequest, servletResponse);
        }

        String apiKey = req.getHeader("X-API-KEY");
        log.info("API key: " + apiKey);

        ApplicationDTO application;
        try {
            application = this.applicationService.getByKey(apiKey);
        } catch (NotFoundException e) {
            e.printStackTrace();
            String msg = "API Key not valid!";

            HttpServletResponse res = (HttpServletResponse) servletResponse;
            res.reset();
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            servletResponse.setContentLength(msg.length());
            servletResponse.getWriter().write(msg);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
