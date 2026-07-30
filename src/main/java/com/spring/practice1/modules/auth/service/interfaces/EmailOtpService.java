package com.spring.practice1.modules.auth.service.interfaces;

import com.spring.practice1.modules.auth.entity.User;

public interface EmailOtpService {
    void generateAndSendOtp(User user);

    boolean verifyOtp(User user, String otp);

}