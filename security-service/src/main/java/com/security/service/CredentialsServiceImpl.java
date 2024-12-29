package com.security.service;

import com.security.model.Credentials;
import com.security.repository.CredentialsRepository;
import jakarta.ws.rs.NotFoundException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CredentialsServiceImpl implements CredentialsService {

    private final CredentialsRepository credentialsRepository;

    @Autowired
    public CredentialsServiceImpl(CredentialsRepository credentialsRepository) {
        this.credentialsRepository = credentialsRepository;
    }

    @Override
    public Credentials getCredentialsByCustomerId(String customerId) {
        Optional<Credentials> credentials = credentialsRepository.findByCustomerCustomerId(customerId);
        if (credentials.isPresent()) {
            return credentials.get();
        } else {
            throw new NotFoundException(String.format("Credentials not found with customerId: %s", customerId));
        }
    }

    @Override
    public Credentials getCredentialsByEmail(String email) {
        Optional<Credentials> credentials = credentialsRepository.findByEmail(email);
        if (credentials.isPresent()) {
            return credentials.get();
        } else {
            throw new NotFoundException(String.format("Credentials not found with email: %s", email));
        }
    }

    @Override
    public Credentials getCredentialsByPhoneNumber(String phoneNumber) {
        Optional<Credentials> credentials = credentialsRepository.findByPhoneNumber(phoneNumber);
        if (credentials.isPresent()) {
            return credentials.get();
        } else {
            throw new NotFoundException(String.format("Credentials not found with phoneNumber: %s", phoneNumber));
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Credentials credentials = new Credentials();

        if (username.matches("^\\d{10}$")) { // If username is phoneNumber
            credentials = getCredentialsByPhoneNumber(username);
        } else if (username.matches("^([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,})$")) { // If username is email
            credentials = getCredentialsByEmail(username);
        } else if (username.matches(
                "^(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$")) { // If username is customerId
            credentials = getCredentialsByCustomerId(username);
        }

        // Creating a dummy set of authorities, need to add roles User model to have authorities
        Set<String> roles = new HashSet<>();
        roles.add("ADMIN");
        roles.add("USER");

        return new User(
                username,
                credentials.getPassword(),
                roles.stream().map(SimpleGrantedAuthority::new).toList());
    }
}
