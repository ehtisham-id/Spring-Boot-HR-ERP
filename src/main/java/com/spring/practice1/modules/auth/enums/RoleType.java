package com.spring.practice1.modules.auth.enums;

import lombok.Getter;

@Getter
public enum RoleType {

    SUPER_ADMIN(
            PermissionType.values()
    ),

    ADMIN(
            PermissionType.USER_READ,
            PermissionType.USER_READ_ALL,
            PermissionType.USER_CREATE,
            PermissionType.USER_UPDATE,
            PermissionType.USER_DELETE
    ),

    MANAGER(
            PermissionType.USER_READ,
            PermissionType.USER_READ_ALL,
            PermissionType.USER_CREATE,
            PermissionType.USER_UPDATE
    ),

    EMPLOYEE(
            PermissionType.USER_READ
    );

    private final PermissionType[] permissions;

    RoleType(PermissionType... permissions) {
        this.permissions = permissions;
    }

    public boolean canCreate(RoleType target) {

        return switch (this) {
            case SUPER_ADMIN -> true;
            case ADMIN -> target == ADMIN || target == EMPLOYEE || target == MANAGER;
            case MANAGER -> target == EMPLOYEE;
            case EMPLOYEE -> false;
        };
    }
}