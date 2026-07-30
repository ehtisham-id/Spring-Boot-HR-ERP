package com.spring.practice1.modules.auth.service.impl;

import com.spring.practice1.modules.auth.service.interfaces.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {


    @Autowired
    private JavaMailSender mailSender;

    @Async("taskExecutor")
    public void sendOtpEmail(String to, String otp){

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject("Email Verification OTP");
        message.setText(
                "Your verification OTP is: "
                        + otp
                        + "\n\nOTP expires in 5 minutes."
        );


        mailSender.send(message);
    }
}