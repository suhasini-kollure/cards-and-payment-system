package com.card.configuraton;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    public OpenAPI customConfig() {
        return new OpenAPI()
                .info(new Info()
                        .title("Card API")
                        .description("Spring boot project for card")
                        .version("0.0.1-SNAPSHOT")
                        .contact(new Contact().name("Suhasini Kollure").email("kolluresuhasinik@gmail.com")));
    }
}
