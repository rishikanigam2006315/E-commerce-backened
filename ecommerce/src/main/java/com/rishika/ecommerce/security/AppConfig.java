package com.rishika.ecommerce.security;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {
    @Value("${app.mode}")
    private String mode;

    public boolean isDev(){
        return "DEV".equalsIgnoreCase(mode);
    }
}
