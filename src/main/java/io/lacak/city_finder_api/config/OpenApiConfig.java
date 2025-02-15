package io.lacak.city_finder_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {
    @Bean
    protected OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                		.title("City Finder API")
                        .description("This is api created for coding challenge of Lacak.io.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Lukman")
                                .email("lukman@lukman.site")));
    }
}
