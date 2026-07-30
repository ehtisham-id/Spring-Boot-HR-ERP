package com.spring.practice1.modules.auth.entity;

import com.spring.practice1.common.entity.BaseEntity;
import com.spring.practice1.modules.auth.enums.PermissionType;
import com.spring.practice1.modules.auth.enums.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_user")
public class User extends BaseEntity {
    @Email
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "f_name")
    private String firstName;

    @Column(name = "l_name")
    private String lastName;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatus status =  UserStatus.ACTIVE;

    @Column(name = "email_verified")
    private boolean emailVerified = false;

    @Column(name = "mfa_enabled")
    private  boolean mfaEnabled = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    public boolean hasPermission(PermissionType permissionType) {
        return role.getPermissions()
                .stream()
                .anyMatch(permission ->
                        permission.getName() == permissionType
                );
    }
}
