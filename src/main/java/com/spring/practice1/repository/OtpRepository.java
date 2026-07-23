package com.spring.practice1.repository;


import com.spring.practice1.entity.User;
import com.spring.practice1.entity.auth.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp, Long> {
    Optional<Otp> findByUser(User user);

}
