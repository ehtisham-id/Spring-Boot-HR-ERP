package com.spring.practice1.modules.auth.service.impl;

import com.spring.practice1.modules.auth.entity.User;
import com.spring.practice1.modules.auth.entity.Otp;
import com.spring.practice1.modules.auth.repository.OtpRepository;
import com.spring.practice1.modules.auth.service.interfaces.EmailOtpService;
import com.spring.practice1.modules.auth.service.interfaces.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class EmailOtpServiceImpl implements EmailOtpService {

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private EmailService emailService;


    private final SecureRandom random = new SecureRandom();

    public void generateAndSendOtp(User user) {

        Otp emailOtp = otpRepository.findByUser(user)
                .orElse(new Otp());

        // Prevent OTP spam // 60 seconds cooldown period
        if (emailOtp.getLastSentAt() != null &&
                emailOtp.getLastSentAt()
                        .plusSeconds(60)
                        .isAfter(LocalDateTime.now())) {

            throw new RuntimeException(
                    "Please wait before requesting another OTP"
            );
        }

        // Limit OTP requests
        if (emailOtp.getSendAttempts() >= 5) {
            assert emailOtp.getLastSentAt() != null;
            if (emailOtp.getLastSentAt()
                    .plusHours(1)
                    .isAfter(LocalDateTime.now())) {

                throw new RuntimeException("Too many OTP requests. Try again later.");
            }
        }

        String otp = generateOtp();

        emailOtp.setUser(user);
        emailOtp.setOtp(otp);
        emailOtp.setExpiryTime(LocalDateTime.now().plusMinutes(5));
        emailOtp.setLastSentAt(LocalDateTime.now());
        emailOtp.setSendAttempts(emailOtp.getSendAttempts() + 1);
        emailOtp.setVerifyAttempts(0);
        emailOtp.setVerified(false);
        otpRepository.save(emailOtp);
        emailService.sendOtpEmail(user.getEmail(), otp);
    }


    public boolean verifyOtp(User user, String otp) {

        Otp emailOtp = otpRepository.findByUser(user).orElse(null);

        if (emailOtp == null) {
            return false;
        }

        if (emailOtp.isVerified()) {
            return false;
        }

        if (emailOtp.getExpiryTime()
                .isBefore(LocalDateTime.now())) {

            return false;
        }

        if (emailOtp.getVerifyAttempts() >= 5) {
            throw new RuntimeException("Too many incorrect attempts");
        }

        emailOtp.setVerifyAttempts(
                emailOtp.getVerifyAttempts() + 1
        );

        if (!emailOtp.getOtp().equals(otp)) {
            otpRepository.save(emailOtp);
            return false;
        }

        emailOtp.setVerified(true);
        otpRepository.save(emailOtp);

        return true;
    }


    private String generateOtp() {
        return String.valueOf(100000 + random.nextInt(900000));
    }
}