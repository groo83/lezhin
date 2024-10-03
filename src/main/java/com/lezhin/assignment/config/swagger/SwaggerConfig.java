package com.lezhin.assignment.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

   @Bean
   public OpenAPI api() {
       OpenAPI openAPI = new OpenAPI()
               .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
               .components(new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()))
               .info(apiInfo());


       return openAPI;
   }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

    private Info apiInfo() {
        return new Info()
                .title("Content Platform API Documentation")
                .description("콘텐츠 플랫폼 API")
                .version("1.0.0");
    }

}