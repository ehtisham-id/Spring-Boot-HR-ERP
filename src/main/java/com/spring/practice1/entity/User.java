package com.spring.practice1.entity;

import com.spring.practice1.enums.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue
    private Long id ;

    @Email
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "f_name")
    private String f_name;

    @Column(name = "l_name")
    private String l_name;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private UserStatus status =  UserStatus.ACTIVE;

    @Column(name = "email_verified")
    private boolean emailVerified = false;
}
