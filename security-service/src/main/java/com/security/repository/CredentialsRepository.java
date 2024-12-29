package com.security.repository;

import com.security.model.Credentials;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CredentialsRepository extends JpaRepository<Credentials, Long> {

    Optional<Credentials> findByCustomerCustomerId(String customerId);

    Optional<Credentials> findByEmail(String email);

    Optional<Credentials> findByPhoneNumber(String phoneNumber);
}
