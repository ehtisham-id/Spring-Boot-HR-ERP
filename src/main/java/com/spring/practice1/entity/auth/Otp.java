package com.spring.practice1.entity.auth;

import com.spring.practice1.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "email_otp")
public class Otp {
    @Id
    @GeneratedValue
    private Long id;


    @OneToOne
    @JoinColumn(name="user_id")
    private User user;


    private String otp;


    private LocalDateTime expiryTime;
}
