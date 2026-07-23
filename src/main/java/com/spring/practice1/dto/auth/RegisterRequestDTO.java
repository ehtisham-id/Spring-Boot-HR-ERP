package com.spring.practice1.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class RegisterRequestDTO {
    @Size(min = 3, max = 25)
    private String f_name;
    @Size(min = 3, max = 25)
    private String l_name;
    @Email
    private  String email;
    @Size(min = 6, max = 20)
    private String password;
}
