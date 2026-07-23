package com.spring.practice1.service.auth;

import com.spring.practice1.entity.User;
import com.spring.practice1.entity.auth.Otp;
import com.spring.practice1.repository.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.Random;


@Service
public class EmailOtpService {


    @Autowired
    private OtpRepository otpRepository;


    @Autowired
    private EmailService emailService;


    public void generateAndSendOtp(User user) {


        String otp =
                String.valueOf(
                        100000 + new Random().nextInt(900000)
                );


        Otp emailOtp =
                otpRepository.findByUser(user)
                        .orElse(new Otp());


        emailOtp.setUser(user);
        emailOtp.setOtp(otp);
        emailOtp.setExpiryTime(
                LocalDateTime.now().plusMinutes(5)
        );


        otpRepository.save(emailOtp);


        emailService.sendOtpEmail(
                user.getEmail(),
                otp
        );
    }


    public boolean verifyOtp(User user, String otp) {


        Otp emailOtp =
                otpRepository.findByUser(user)
                        .orElse(null);


        if (emailOtp == null)
            return false;


        if (emailOtp.getExpiryTime()
                .isBefore(LocalDateTime.now()))
            return false;


        return emailOtp.getOtp().equals(otp);

    }

}