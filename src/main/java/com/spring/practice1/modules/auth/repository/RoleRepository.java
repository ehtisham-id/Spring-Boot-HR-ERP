package com.spring.practice1.modules.auth.repository;

import com.spring.practice1.modules.auth.entity.Permission;
import com.spring.practice1.modules.auth.entity.Role;
import com.spring.practice1.modules.auth.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleType name);
}
