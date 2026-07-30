package com.spring.practice1.modules.auth.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record VerifyOtpRequest(@Email String email,@Size(min = 6,max = 6) String otp) {
}