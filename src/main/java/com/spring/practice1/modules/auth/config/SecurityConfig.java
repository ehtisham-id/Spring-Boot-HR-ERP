package com.spring.practice1.modules.auth.config;

import com.spring.practice1.modules.auth.filter.JwtAuthenticationFilter;
import com.spring.practice1.modules.auth.service.impl.CustomUserDetailServiceImpl;
import com.spring.practice1.modules.auth.utils.OAuth2SuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomUserDetailServiceImpl customUserDetailService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private OAuth2SuccessHandler oAuth2SuccessHandler;

    public SecurityConfig(
            CustomUserDetailServiceImpl customUserDetailService,
            JwtAuthenticationFilter jwtAuthenticationFilter
    ) {
        this.customUserDetailService = customUserDetailService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login", "/auth/send-otp", "/auth/verify-otp").permitAll()
                        .anyRequest().authenticated()
                )

                .userDetailsService(customUserDetailService)

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                .oauth2Login(oauth -> oauth.successHandler(oAuth2SuccessHandler))
                .formLogin(Customizer.withDefaults())
                .build();
    }


    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {

        return configuration.getAuthenticationManager();
    }
}
