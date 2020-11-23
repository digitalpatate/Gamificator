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

        if (!req.getRequestURI().startsWith("/") || (req.getRequestURI().startsWith("/applications") && req.getMethod().startsWith("POST"))) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String apiKey = req.getHeader("X-API-KEY");
        log.info("API key: " + apiKey);

        String msg = "API Key not valid!";
        ApplicationDTO application = null;
        try {
            application = this.applicationService.getByKey(apiKey);
        } catch (NotFoundException e) {
            log.info(msg);
        }

        if(application == null){
            HttpServletResponse res = (HttpServletResponse) servletResponse;
            res.reset();
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            servletResponse.setContentLength(msg.length());
            servletResponse.getWriter().write(msg);
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
