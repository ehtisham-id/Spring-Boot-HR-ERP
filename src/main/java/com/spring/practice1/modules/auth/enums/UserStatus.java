package com.spring.practice1.modules.auth.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.spring.practice1.enums.ApplicationStatus;

public enum UserStatus {
    ACTIVE, INACTIVE, PENDING;

    @JsonCreator
    public static ApplicationStatus fromString(String value) {
        for (ApplicationStatus s : ApplicationStatus.values()) {
            if (s.name().equalsIgnoreCase(value)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Invalid status: " + value);
    }
}
