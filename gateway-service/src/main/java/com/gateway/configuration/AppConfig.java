package com.gateway.configuration;

import java.util.Map;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public InfoContributor infoContributor() {
        return builder -> builder.withDetail(
                        "application",
                        Map.of(
                                "name",
                                "security-gateway",
                                "description",
                                "Spring Cloud Gateway application to route the traffic",
                                "version",
                                "0.0.1-SNAPSHOT"))
                .build();
    }
}
