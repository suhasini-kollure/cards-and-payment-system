package com.customer.service;

import com.customer.dto.RegisterCustomer;
import com.customer.dto.UpdateCustomer;
import com.customer.model.Customer;

public interface CustomerService {

    Customer saveCustomer(RegisterCustomer registerCustomer);

    Customer getCustomerById(String customerId);

    Customer getCustomerByEmail(String email);

    Customer getCustomerByPhoneNumber(String phoneNumber);

    Customer updateCustomer(String customerId, UpdateCustomer updateCustomer);

    String deleteCustomer(String customerId);

    Customer getCustomerByCardNumber(String cardNumber);
}
