package com.gateway.filter;

import java.util.List;
import java.util.function.Predicate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class RouteValidator {

    private static final List<String> OPEN_API_ENDPOINTS = List.of(
            "/customer/save",
            "/security/login",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/actuator/**");

    public Predicate<ServerHttpRequest> isSecured = serverHttpRequest -> {
        String path = serverHttpRequest.getURI().getPath();
        return OPEN_API_ENDPOINTS.stream().noneMatch(excludedPath -> {
            if (excludedPath.contains("**")) {
                String regex = excludedPath.replace("**", ".*");
                return path.matches(regex);
            }
            return path.startsWith(excludedPath);
        });
    };
}
