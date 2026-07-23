package com.spring.practice1.service.auth;

import com.spring.practice1.dto.auth.LoginRequestDTO;
import com.spring.practice1.dto.auth.RegisterRequestDTO;
import com.spring.practice1.dto.auth.UserResponseDTO;
import com.spring.practice1.entity.User;
import com.spring.practice1.repository.UserRepository;
import com.spring.practice1.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailService userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailOtpService emailOtpService;

    public UserResponseDTO login(LoginRequestDTO req) {
        User dbUser = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!dbUser.isEmailVerified()) {
            throw new RuntimeException("Please verify your email first");
        }


        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
        );

        UserDetails user = userDetailsService.loadUserByUsername(req.getEmail());
        String token = jwtUtils.generateToken(user);

        return new UserResponseDTO(user.getUsername(), token);
    }

    public String register(RegisterRequestDTO req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new RuntimeException(
                    "Email already registered"
            );
        }

        User user = new User();
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setF_name(req.getF_name());
        user.setL_name(req.getL_name());
        user.setEmailVerified(false);

        userRepository.save(user);
        emailOtpService.generateAndSendOtp(user);
        return "User Registered Successfully.  OTP sent to email. Please verify!";
    }

    public boolean verifyEmail(String email, String otp) {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new RuntimeException("User not found")
        );

        boolean valid = emailOtpService.verifyOtp(user, otp);


        if (valid) {
            user.setEmailVerified(true);
            userRepository.save(user);
        }
        return valid;
    }


    public void resendOtp(String email) {

        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new RuntimeException("User not found")
        );
        if(user.isEmailVerified()){
            throw new RuntimeException("Email is already verified");
        }
        emailOtpService.generateAndSendOtp(user);

    }
}
