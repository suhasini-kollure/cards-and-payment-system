package com.gateway.filter;

import com.gateway.utility.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AbstractGatewayFilterFactory.NameConfig> {

    private final RouteValidator validator;
    private final JWTUtil jwtUtil;

    @Autowired
    public AuthenticationFilter(RouteValidator validator, JWTUtil jwtUtil) {
        super(NameConfig.class);
        this.validator = validator;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public GatewayFilter apply(NameConfig config) {
        return (exchange, chain) -> {
            var request = exchange.getRequest();

            if (validator.isSecured.test(request)) {
                String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    throw new ResponseStatusException(
                            HttpStatus.UNAUTHORIZED, "Missing or invalid authorization header");
                }

                String token = authHeader.substring(7);
                try {
                    boolean validateToken = jwtUtil.validateToken(token, jwtUtil.getTokenUsername(token));
                    if (!validateToken) {
                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Failed to validate token");
                    }
                } catch (Exception e) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired token", e);
                }
            }
            return chain.filter(exchange);
        };
    }
}
