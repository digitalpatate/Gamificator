package ch.heigvd.amt.gamificator.configuration;

import com.fasterxml.jackson.core.type.ResolvedType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.*;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.HttpAuthenticationBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import static java.util.Collections.singletonList;


@Configuration
@EnableWebMvc
public class SwaggerDocumentationConfig extends WebMvcConfigurerAdapter {

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Fruits API")
                .description("An API to demonstrate Swagger and Spring Boot")
                .version("0.2.0")
                .build();
    }

    @Bean
    public Docket customImplementation(){
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage("ch.heigvd.amt.gamificator.api"))
                .build()
                .directModelSubstitute(Void.class, Void.class)
                .securitySchemes(singletonList(apikey()))
                .securityContexts(singletonList(securityContext()))
                .apiInfo(apiInfo());
    }

    private SecurityScheme apikey() {
        /*
        return new HttpAuthenticationBuilder()
          .name("BearerAuthorization")
          .scheme("bearer")
          .build();
         */
        return new ApiKey("x-api-key", "api_key", "header");
    }


    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(securityReferences())
                .build();
    }

    private List<SecurityReference> securityReferences() {

        List<SecurityReference> securityReferences = new LinkedList<>();
        securityReferences.add(new SecurityReference("x-api-key", new AuthorizationScope[] {}));
        return securityReferences;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("**").addResourceLocations("classpath:/dist/");

        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
