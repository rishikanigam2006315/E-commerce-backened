package com.rishika.ecommerce.auth;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;

}
