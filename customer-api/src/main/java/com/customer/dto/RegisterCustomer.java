package com.customer.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegisterCustomer {

    @NotEmpty(message = "Customer ID is required.")
    @Pattern(
            regexp = "^(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$",
            message = "Please enter a valid customerId.")
    private String customerId;

    @NotEmpty(message = "Name is required.")
    @Pattern(
            regexp = "^[a-zA-Z]{2,20}$",
            message = "Name must only contain letters and be between 2 and 20 characters.")
    private String name;

    @NotEmpty(message = "Age is required.")
    @Pattern(regexp = "\\d{1,3}", message = "Age must be a numeric value.")
    private String age;

    @NotEmpty(message = "Address is required.")
    @Pattern(regexp = "^.{5,100}$", message = "Address must be between 5 and 100 characters.")
    private String address;

    @NotEmpty(message = "Account number is required.")
    @Pattern(regexp = "^\\d{12}$", message = "Account number must contain exactly 12 digits.")
    private String accountNumber;

    @NotEmpty(message = "IFSC is required.")
    @Pattern(
            regexp = "^[a-zA-Z0-9]{8}$",
            message = "IFSC must only contain letters and digits, and the length must be exactly 8 characters.")
    private String ifsc;

    @NotEmpty(message = "Branch is required.")
    @Pattern(
            regexp = "^[a-zA-Z]{5,20}$",
            message = "Branch must only contain letters and be between 5 and 20 characters.")
    private String branch;

    @NotEmpty(message = "Email is required.")
    @Pattern(regexp = "^([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,})$", message = "Please enter a valid email.")
    private String email;

    @NotEmpty(message = "Phone number is required.")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must contain exactly 10 digits.")
    private String phoneNumber;

    @NotEmpty(message = "Password is required.")
    @Pattern(regexp = "^.{4,}$", message = "Password length must be at least 4 characters.")
    private String password;
}
