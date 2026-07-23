package com.spring.practice1.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequestDTO {
    @Email
    private String email;
    @Size(min = 6, max = 20)
    private String password;
}
