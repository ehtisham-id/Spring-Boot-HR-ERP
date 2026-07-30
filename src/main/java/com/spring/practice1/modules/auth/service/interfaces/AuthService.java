package com.spring.practice1.modules.auth.service.interfaces;

import com.spring.practice1.modules.auth.dto.request.LoginRequest;
import com.spring.practice1.modules.auth.dto.request.RegisterRequest;
import com.spring.practice1.modules.auth.dto.response.TokenResponse;

public interface AuthService {
    TokenResponse login(LoginRequest req) ;
    String register(RegisterRequest req) ;
    boolean verifyEmail(String email, String otp);
    String resendOtp(String email);
}
