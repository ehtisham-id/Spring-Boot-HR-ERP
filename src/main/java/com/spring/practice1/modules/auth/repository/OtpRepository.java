package com.spring.practice1.modules.auth.repository;


import com.spring.practice1.modules.auth.entity.User;
import com.spring.practice1.modules.auth.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp, Long> {
    Optional<Otp> findByUser(User user);
}
