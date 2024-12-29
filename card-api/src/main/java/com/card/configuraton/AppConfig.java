package com.card.configuraton;

import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public InfoContributor infoContributor() {
        return builder -> builder.withDetail(
                        "application",
                        Map.of(
                                "name",
                                "card-api",
                                "description",
                                "SpringBoot application to manage card operations",
                                "version",
                                "0.0.1-SNAPSHOT"))
                .build();
    }
}
