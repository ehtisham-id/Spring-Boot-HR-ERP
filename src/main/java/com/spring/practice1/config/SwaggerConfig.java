package com.spring.practice1.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI myOpenApi(){
        Info info = new Info()
                .title("ERP System For Employee")
                .version("1.0.0")
                .description("The Documentation for  my RestAPI");

        return new OpenAPI().info(info);
    }
}
