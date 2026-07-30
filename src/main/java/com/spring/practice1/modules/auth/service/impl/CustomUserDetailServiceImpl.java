package com.spring.practice1.modules.auth.service.impl;


import com.spring.practice1.modules.auth.entity.CustomUserDetail;
import com.spring.practice1.modules.auth.entity.User;
import com.spring.practice1.modules.auth.repository.UserRepository;
import com.spring.practice1.modules.auth.service.interfaces.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailServiceImpl implements CustomUserDetailService {
    private final UserRepository userRepository;

    public User getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();

        return userDetails.getUser();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("User not Found")
        );
        return new CustomUserDetail(user);
    }
}
