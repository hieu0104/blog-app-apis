package com.hieu.blog.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.security.core.context.SecurityContext;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration

@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(

                contact = @Contact(

                        name = "HieuLe",

                        email = "lechihieu.at.@gamail.com"


                ),

                description = " Blog app apis ",

                title = "  Blog app apis backend "


        )

)

public class SpringDocConfig {
   /* @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI().info(new Info().title("Blog app apis").version("1.0.0"));
    }*/

   /* @Bean
    public GroupedOpenApi httpApi() {
        return GroupedOpenApi.builder()
                .group("http")
                .pathsToMatch("/**")
                .build();
    }*/


    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().addSecurityItem(new SecurityRequirement().
                        addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes
                        ("Bearer Authentication", createAPIKeyScheme()))
                .info(new Info().title("My REST API")
                        .description("Some custom description of API.")
                        .version("1.0").contact(new io.swagger.v3.oas.models.info.Contact().name("Sallo Szrajbman")
                                .email("www.baeldung.com").url("salloszraj@gmail.com"))
                        .license(new License().name("License of API")
                                .url("API license URL")));
    }
}
