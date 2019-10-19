package com.example.common.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI openAPI(@Value("${spring.application.name}") final String title,
                           @Value("${springdoc.server.base-url}") final String baseUrl,
                           @Value("${server.servlet.context-path}") final String contextPath) {
        return new OpenAPI()
                .info(new Info().title(title))
                .addServersItem(new Server().url(baseUrl + contextPath));
    }
}
