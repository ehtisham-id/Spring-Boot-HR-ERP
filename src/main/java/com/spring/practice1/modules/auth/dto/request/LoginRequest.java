package com.spring.practice1.modules.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record LoginRequest (
    @Email
    String email,
    @Size(min = 6, max = 20)
    String password
){}
