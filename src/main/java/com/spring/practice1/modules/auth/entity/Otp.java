package com.spring.practice1.modules.auth.entity;

import com.spring.practice1.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "email_otp")
public class Otp extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(nullable = false)
    private String otp;

    @Column(nullable = false)
    private LocalDateTime expiryTime;

    private LocalDateTime lastSentAt;

    private int sendAttempts = 0;

    private int verifyAttempts = 0;

    private boolean verified = false;
}