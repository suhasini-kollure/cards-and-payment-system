package com.security.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String customerIdOrEmailOrPhoneNo;
    private String password;
}
