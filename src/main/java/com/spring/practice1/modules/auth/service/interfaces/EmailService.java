package com.spring.practice1.modules.auth.service.interfaces;

public interface EmailService {
    void sendOtpEmail(String to, String otp);
}