package ch.heigvd.amt.gamificator.configuration;

import ch.heigvd.amt.gamificator.api.application.ApplicationService;
import ch.heigvd.amt.gamificator.filter.APIKeyAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
@EnableWebSecurity
@Order(1)
public class APISecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("x-api-key")
    private String keyHeaderName;

    @Value("signature")
    private String secretHeaderName;

    @Autowired
    private ApplicationService applicationService;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        APIKeyAuthFilter filter = new APIKeyAuthFilter(keyHeaderName, secretHeaderName);

        filter.setAuthenticationManager(new AuthenticationManager() {

            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                String[] principal = (String[]) authentication.getPrincipal();
                authentication.setAuthenticated(false);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                if (applicationService.canBeAuthenticated(principal)) {
                    authentication.setAuthenticated(true);
                } else {
                    throw new BadCredentialsException("The API key was not found or not the expected value.");
                }

                return authentication;
            }

        });


        httpSecurity.authorizeRequests()
                    .antMatchers("/","/swagger-ui/**","/swagger-resources/**","/v3/api-docs/**","/applications","/swagger-ui.html","/swagger-**","/healthcheck")
                    .permitAll();

        httpSecurity.antMatcher("/**")
                    .csrf()
                    .disable()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .addFilter(filter)
                    .authorizeRequests()
                    .anyRequest()
                    .authenticated();
    }

}