package com.hieu.blog.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration

@OpenAPIDefinition(
		info = @io.swagger.v3.oas.annotations.info.Info(
				
				contact = @Contact(
						
						name = "HieuLe",
						
						email="lechihieu.at.@gamail.com"
						
						
						),
				
				description= " Blog app apis ",
				
				title="  Blog app apis backend "
				
				
				
				
				)
		
		)

public class SpringDocConfig {
    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI().info(new Info().title("Blog app apis").version("1.0.0"));
    }

    @Bean
    public GroupedOpenApi httpApi() {
        return GroupedOpenApi.builder()
                .group("http")
                .pathsToMatch("/**")
                .build();
    }
}