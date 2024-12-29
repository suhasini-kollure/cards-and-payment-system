package com.security.controller;

import com.security.dto.LoginRequest;
import com.security.dto.LoginResponse;
import com.security.utility.JWTUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@Tag(name = "Security Controller", description = "Controller for security operations")
public class SecurityController {

    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public SecurityController(JWTUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @Operation(summary = "Login user", description = "Endpoint to login user")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getCustomerIdOrEmailOrPhoneNo(), loginRequest.getPassword()));

        LoginResponse loginResponse = new LoginResponse();

        if (authentication.isAuthenticated()) {
            String token = jwtUtil.generateToken(loginRequest.getCustomerIdOrEmailOrPhoneNo());
            loginResponse.setToken(token);
            loginResponse.setMessage("Login successful! JWT token generated.");
            return new ResponseEntity<>(loginResponse, HttpStatus.OK);
        } else {
            loginResponse.setMessage("Authentication failed!");
            return new ResponseEntity<>(loginResponse, HttpStatus.UNAUTHORIZED);
        }
    }
}
