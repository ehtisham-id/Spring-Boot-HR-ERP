package com.spring.practice1.modules.auth.service.impl;

import com.spring.practice1.modules.auth.dto.request.LoginRequest;
import com.spring.practice1.modules.auth.dto.request.RegisterRequest;
import com.spring.practice1.modules.auth.dto.response.TokenResponse;
import com.spring.practice1.modules.auth.entity.Role;
import com.spring.practice1.modules.auth.entity.User;
import com.spring.practice1.modules.auth.enums.PermissionType;
import com.spring.practice1.modules.auth.repository.RoleRepository;
import com.spring.practice1.modules.auth.repository.UserRepository;
import com.spring.practice1.modules.auth.service.interfaces.AuthService;
import com.spring.practice1.modules.auth.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailServiceImpl userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailOtpServiceImpl emailOtpService;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public TokenResponse login(LoginRequest req) {
        User dbUser = userRepository.findByEmail(req.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!dbUser.isEmailVerified()) {
            throw new RuntimeException("Please verify your email first");
        }


        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.email(), req.password())
        );

        UserDetails user = userDetailsService.loadUserByUsername(req.email());
        String token = jwtUtils.generateToken(user);

        return new TokenResponse(user.getUsername(), token);
    }

    @Override
    @PreAuthorize("hasAuthority('USER_CREATE')")
    public String register(RegisterRequest req) {
        Role role =
                roleRepository.findByName(
                                req.role()
                        )
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Role not found")
                        );


        User user = new User();

        user.setEmail(req.email());

        user.setPassword(
                passwordEncoder.encode(
                        req.password()
                )
        );
        user.setFirstName(req.firstName());
        user.setLastName(req.lastName());
        user.setRole(role);
        userRepository.save(user);

        return "User Registered Successfully !";
    }

    @Override
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


    @Override
    public String resendOtp(String email) {

        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new RuntimeException("User not found")
        );
        if(user.isEmailVerified()){
            throw new RuntimeException("Email is already verified");
        }
        emailOtpService.generateAndSendOtp(user);

        return "OTP Sent Successfully !";

    }
}
