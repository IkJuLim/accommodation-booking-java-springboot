package com.github.IkJuLim.accommodation_booking.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(apiInfo())
                .servers(List.of(new Server().url("/")))
                .components(components())
                .addSecurityItem(new SecurityRequirement().addList("cookie-auth"));
    }

    private Info apiInfo() {
        return new Info()
                .title("Accommodation Booking API")
                .description("Accommodation Booking API Documentation")
                .version("v1");
    }

    private Components components() {
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.COOKIE)
                .name("JSESSIONID");

        return new Components().addSecuritySchemes("cookie-auth", securityScheme);
    }
}