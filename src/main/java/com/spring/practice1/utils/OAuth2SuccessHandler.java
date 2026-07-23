package com.spring.practice1.utils;

import com.spring.practice1.dto.auth.UserResponseDTO;
import com.spring.practice1.entity.User;
import com.spring.practice1.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException {

        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();

        String email = oauthUser.getAttribute("email");
        String firstName = oauthUser.getAttribute("given_name");
        String lastName = oauthUser.getAttribute("family_name");

        User user = userRepository.findByEmail(email)
                .orElseGet(() -> {

                    User u = new User();
                    u.setEmail(email);
                    u.setF_name(firstName);
                    u.setL_name(lastName);
                    u.setEmailVerified(true);
                    return userRepository.save(u);
                });

        UserDetails userDetails =
                org.springframework.security.core.userdetails.User
                        .withUsername(user.getEmail())
                        .password("")
                        .build();

        String jwt = jwtUtils.generateToken(userDetails);

        response.setContentType("application/json");

        response.getWriter().write("token :" + jwt);
    }
}
