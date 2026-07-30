package com.spring.practice1.modules.auth.repository;

import com.spring.practice1.modules.auth.entity.Permission;
import com.spring.practice1.modules.auth.enums.PermissionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findByName(PermissionType name);
}
