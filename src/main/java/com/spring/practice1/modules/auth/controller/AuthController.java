package com.spring.practice1.modules.auth.controller;

import com.spring.practice1.modules.auth.dto.request.LoginRequest;
import com.spring.practice1.modules.auth.dto.request.RegisterRequest;
import com.spring.practice1.modules.auth.dto.response.TokenResponse;
import com.spring.practice1.modules.auth.dto.request.VerifyOtpRequest;
import com.spring.practice1.modules.auth.service.impl.AuthServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthServiceImpl authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyEmail(@RequestBody @Valid VerifyOtpRequest request) {
        boolean verified = authService.verifyEmail(request.email(), request.otp());

        if (verified) {
            return ResponseEntity.ok("Email verified successfully");
        }

        return ResponseEntity.badRequest().body("Invalid or expired OTP");
    }

    @PostMapping("/send-otp")
    public ResponseEntity<String> resendOtp(@RequestParam String email) {
        authService.resendOtp(email);
        return ResponseEntity.ok("OTP sent successfully!!!");
    }
}
