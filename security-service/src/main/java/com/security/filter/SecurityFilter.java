package com.security.filter;

import com.security.service.CredentialsService;
import com.security.utility.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
public class SecurityFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final CredentialsService credentialsService;

    @Autowired
    public SecurityFilter(JWTUtil jwtUtil, CredentialsService credentialsService) {
        this.jwtUtil = jwtUtil;
        this.credentialsService = credentialsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        String token = null;
        String tokenUsername = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            tokenUsername = jwtUtil.getTokenUsername(token);
        }

        if (tokenUsername != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = credentialsService.loadUserByUsername(tokenUsername);
            boolean isValid = jwtUtil.validateToken(token, userDetails.getUsername());

            if (isValid) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        tokenUsername, userDetails.getPassword(), userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
