package com.spring.practice1.modules.auth.entity;

import com.spring.practice1.common.entity.BaseEntity;
import com.spring.practice1.modules.auth.enums.RoleType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private RoleType name;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Permission> permissions;
}
