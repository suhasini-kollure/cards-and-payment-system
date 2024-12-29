package com.customer.configuration;

import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Configuration
public class AppConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

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
                                "customer-api",
                                "description",
                                "SpringBoot application to manage customer operations",
                                "version",
                                "0.0.1-SNAPSHOT"))
                .build();
    }
}
