package com.customer.controller;

import com.customer.dto.*;
import com.customer.model.Customer;
import com.customer.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("customer")
@Tag(name = "Customer Controller", description = "Controller for customer operations")
public class CustomerController {

    private final CustomerService customerService;
    private final RestTemplate restTemplate;

    @Autowired
    public CustomerController(CustomerService customerService, RestTemplate restTemplate) {
        this.customerService = customerService;
        this.restTemplate = restTemplate;
    }

    @Operation(summary = "Save Customer", description = "Endpoint to save a new customer")
    @PostMapping("/save")
    public ResponseEntity<Customer> saveCustomer(@Valid @RequestBody RegisterCustomer registerCustomer) {
        Customer savedCustomer = customerService.saveCustomer(registerCustomer);
        return new ResponseEntity<>(savedCustomer, HttpStatus.OK);
    }

    @Operation(summary = "Get Customer", description = "Endpoint to get a already existing customer")
    @GetMapping("/getCustomerById/{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("customerId") String customerId) {
        Customer customer = customerService.getCustomerById(customerId);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @Operation(summary = "Get Customer by Email", description = "Endpoint to get a existing customer by email")
    @GetMapping("/getCustomerByEmail/{email}")
    public ResponseEntity<Customer> getCustomerByEmail(@PathVariable("email") String email) {
        Customer customerByEmail = customerService.getCustomerByEmail(email);
        return new ResponseEntity<>(customerByEmail, HttpStatus.OK);
    }

    @Operation(
            summary = "Get Customer by phoneNumber",
            description = "Endpoint to get a existing customer by phoneNumber")
    @GetMapping("/getCustomerByPhoneNumber/{phoneNumber}")
    public ResponseEntity<Customer> getCustomerByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber) {
        Customer customerByPhoneNumber = customerService.getCustomerByPhoneNumber(phoneNumber);
        return new ResponseEntity<>(customerByPhoneNumber, HttpStatus.OK);
    }

    @Operation(summary = "Update Customer", description = "Endpoint to update a existing customer by customer id")
    @PatchMapping("/update/{customerId}")
    public ResponseEntity<Customer> updateCustomer(
            @PathVariable("customerId") String customerId, @Valid @RequestBody UpdateCustomer updateCustomer) {
        Customer customer = customerService.updateCustomer(customerId, updateCustomer);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @Operation(summary = "Delete Customer", description = "Endpoint to delete a customer by customer id")
    @DeleteMapping("/delete/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("customerId") String customerId) {
        String deletedCustomer = customerService.deleteCustomer(customerId);
        return new ResponseEntity<>(deletedCustomer, HttpStatus.OK);
    }

    @Operation(summary = "Get Customer by cardNumber", description = "Endpoint to get a customer by cardNumber")
    @GetMapping("/getCustomerByCardNumber/{cardNumber}")
    public ResponseEntity<Customer> getCustomerByCardNumber(@PathVariable("cardNumber") String cardNumber) {
        Customer customer = customerService.getCustomerByCardNumber(cardNumber);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }
}
