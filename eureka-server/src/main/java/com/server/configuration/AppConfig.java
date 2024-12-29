package com.server.configuration;

import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class AppConfig {

    @Bean
    public InfoContributor infoContributor() {
        return builder -> builder
                .withDetail("application",
                        Map.of("name", "eureka-server",
                                "description", "SpringBoot application for registry service",
                                "version", "0.0.1-SNAPSHOT"))
                .build();
    }
}

