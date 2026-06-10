package com.scout.management.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Kenya Scout Management API")
                        .description("REST API for managing Kenya Scout Association members, units, registrations, approvals, finance, and reporting. All endpoints except /api/auth/** require a valid JWT token.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Kenya Scouts Association")
                                .email("admin@scout.co.ke"))
                        .license(new License()
                                .name("Private")
                                .url("https://scout.co.ke")))
                .addSecurityItem(new SecurityRequirement().addList("Bearer"))
                .components(new Components()
                        .addSecuritySchemes("Bearer",
                                new SecurityScheme()
                                        .name("Bearer")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Enter JWT token from /api/auth/login")));
    }
}
