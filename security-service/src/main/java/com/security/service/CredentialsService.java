package com.security.service;

import com.security.model.Credentials;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CredentialsService extends UserDetailsService {

    Credentials getCredentialsByCustomerId(String customerId);

    Credentials getCredentialsByEmail(String email);

    Credentials getCredentialsByPhoneNumber(String phoneNumber);
}
