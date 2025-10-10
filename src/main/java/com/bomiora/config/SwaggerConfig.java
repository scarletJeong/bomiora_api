package com.bomiora.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Bomiora API")
                        .version("1.0.0")
                        .description("Bomiora 쇼핑몰 API 문서")
                        .contact(new Contact()
                                .name("Bomiora Team")
                                .email("admin@bomiora.com")));
    }
}