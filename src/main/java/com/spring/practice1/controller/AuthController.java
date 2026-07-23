package com.spring.practice1.controller;

import ch.qos.logback.core.model.Model;
import com.spring.practice1.dto.auth.LoginRequestDTO;
import com.spring.practice1.dto.auth.RegisterRequestDTO;
import com.spring.practice1.dto.auth.UserResponseDTO;
import com.spring.practice1.dto.auth.VerifyOtpRequestDTO;
import com.spring.practice1.service.auth.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequestDTO request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(
            @RequestBody VerifyOtpRequestDTO request
    ) {
        boolean verified = authService.verifyEmail(request.getEmail(), request.getOtp());

        if (verified) {
            return ResponseEntity.ok("Email verified successfully");
        }

        return ResponseEntity.badRequest()
                .body("Invalid or expired OTP");
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<String> resendOtp(@RequestParam String email) {

        authService.resendOtp(email);
        return ResponseEntity.ok("OTP sent again");
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("JWT Authentication Working");
    }
}
