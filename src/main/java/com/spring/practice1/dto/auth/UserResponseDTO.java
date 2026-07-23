package com.spring.practice1.dto.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class UserResponseDTO {
    private String email;
    private String token;

    public UserResponseDTO(String username, String token) {
        this.email = username;
        this.token = token;
    }
}
