package com.customer.repository;

import com.customer.model.Customer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    boolean existsByAccountNumber(String accountNumber);

    Optional<Customer> findByCredentialsEmail(String email);

    Optional<Customer> findByCredentialsPhoneNumber(String phoneNumber);

    Optional<Customer> findCustomerByCardsCardNumber(String cardNumber);
}
