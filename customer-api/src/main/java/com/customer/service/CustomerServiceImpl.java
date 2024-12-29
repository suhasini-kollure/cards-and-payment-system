package com.customer.service;

import com.customer.dto.RegisterCustomer;
import com.customer.dto.UpdateCustomer;
import com.customer.model.Credentials;
import com.customer.model.Customer;
import com.customer.repository.CredentialsRepository;
import com.customer.repository.CustomerRepository;
import jakarta.ws.rs.NotFoundException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CredentialsRepository credentialsRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomerServiceImpl(
            CustomerRepository customerRepository,
            CredentialsRepository credentialsRepository,
            PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.credentialsRepository = credentialsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Customer saveCustomer(RegisterCustomer registerCustomer) {
        if (customerRepository.existsById(registerCustomer.getCustomerId())) {
            log.error("Customer already exists with customerId : {}", registerCustomer.getCustomerId());
            throw new DataIntegrityViolationException(
                    String.format("Customer already exists with customerId : %s", registerCustomer.getCustomerId()));
        }
        if (customerRepository.existsByAccountNumber(registerCustomer.getAccountNumber())) {
            log.error("Customer already exists with accountNumber : {}", registerCustomer.getAccountNumber());
            throw new DataIntegrityViolationException(String.format(
                    "Customer already exists with accountNumber : %s", registerCustomer.getAccountNumber()));
        }
        if (credentialsRepository.existsByEmail(registerCustomer.getEmail())) {
            log.error("Customer already exists with email : {}", registerCustomer.getEmail());
            throw new DataIntegrityViolationException(
                    String.format("Customer already exists with email : %s", registerCustomer.getEmail()));
        }
        if (credentialsRepository.existsByPhoneNumber(registerCustomer.getPhoneNumber())) {
            log.error("Customer already exists with phoneNumber : {}", registerCustomer.getPhoneNumber());
            throw new DataIntegrityViolationException(
                    String.format("Customer already exists with phone number : %s", registerCustomer.getPhoneNumber()));
        }

        Customer customer = new Customer();
        customer.setCustomerId(registerCustomer.getCustomerId());
        customer.setName(registerCustomer.getName());
        customer.setAge(registerCustomer.getAge());
        customer.setAddress(registerCustomer.getAddress());
        customer.setAccountNumber(registerCustomer.getAccountNumber());
        customer.setIfsc(registerCustomer.getIfsc());
        customer.setBranch(registerCustomer.getBranch());
        customerRepository.save(customer);

        Credentials credentials = new Credentials();
        credentials.setEmail(registerCustomer.getEmail());
        credentials.setPhoneNumber(registerCustomer.getPhoneNumber());
        credentials.setPassword(passwordEncoder.encode(registerCustomer.getPassword()));
        credentials.setCustomer(customer);
        credentialsRepository.save(credentials);

        customer.setCredentials(credentials);
        return customerRepository.save(customer);
    }

    @Override
    public Customer getCustomerById(String customerId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isPresent()) {
            log.info("Customer found with customerId : {}", customerId);
            return optionalCustomer.get();
        } else {
            log.error("Customer not found with customerId : {}", customerId);
            throw new NotFoundException(String.format("Customer not found with customerId : %s", customerId));
        }
    }

    @Override
    public Customer getCustomerByEmail(String email) {
        Optional<Customer> optionalCustomer = customerRepository.findByCredentialsEmail(email);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            log.info("Customer found with email : {}", email);
            return customer;
        } else {
            log.error("Customer not found with email {}", email);
            throw new NotFoundException(String.format("Customer not found with email : %s", email));
        }
    }

    @Override
    public Customer getCustomerByPhoneNumber(String phoneNumber) {
        Optional<Customer> optionalCustomer = customerRepository.findByCredentialsPhoneNumber(phoneNumber);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            log.info("Customer found with phoneNumber : {}", phoneNumber);
            return customer;
        } else {
            log.error("Customer not found with phoneNumber : {}", phoneNumber);
            throw new NotFoundException(String.format("Customer not found with phoneNumber : %s", phoneNumber));
        }
    }

    @Override
    public Customer updateCustomer(String customerId, UpdateCustomer updateCustomer) {

        Customer customer = getCustomerById(customerId);

        if (updateCustomer.getName() != null) {
            customer.setName(updateCustomer.getName());
        }
        if (updateCustomer.getAge() != null) {
            customer.setAge(updateCustomer.getAge());
        }
        if (updateCustomer.getAddress() != null) {
            customer.setAddress(updateCustomer.getAddress());
        }
        if (updateCustomer.getAccountNumber() != null) {
            customer.setAccountNumber(updateCustomer.getAccountNumber());
        }
        if (updateCustomer.getIfsc() != null) {
            customer.setIfsc(updateCustomer.getIfsc());
        }
        customerRepository.save(customer);

        Credentials credentials = customer.getCredentials();
        if (updateCustomer.getEmail() != null) {
            credentials.setEmail(updateCustomer.getEmail());
        }
        if (updateCustomer.getPhoneNumber() != null) {
            credentials.setPhoneNumber(updateCustomer.getPhoneNumber());
        }
        if (updateCustomer.getPassword() != null) {
            if (passwordEncoder.matches(updateCustomer.getPassword(), credentials.getPassword())) {
                throw new IllegalArgumentException("Please enter different password!");
            }
            credentials.setPassword(passwordEncoder.encode(updateCustomer.getPassword()));
        }
        customer.setCredentials(credentials);

        credentialsRepository.save(credentials);
        Customer updatedCustomer = customerRepository.save(customer);
        log.info("Customer updated for customerId : {}", customer.getCustomerId());
        return updatedCustomer;
    }

    @Override
    public String deleteCustomer(String customerId) {
        Customer customer = getCustomerById(customerId);
        customerRepository.deleteById(customer.getCustomerId());
        log.info("Customer deleted with customerId : {}", customerId);
        return String.format("Customer deleted with customerId : %s", customerId);
    }

    @Override
    public Customer getCustomerByCardNumber(String cardNumber) {
        Optional<Customer> optionalCustomer = customerRepository.findCustomerByCardsCardNumber(cardNumber);
        if (optionalCustomer.isPresent()) {
            return optionalCustomer.get();
        } else {
            throw new NotFoundException(String.format("Customer not found with cardNumber : %s", cardNumber));
        }
    }
}
