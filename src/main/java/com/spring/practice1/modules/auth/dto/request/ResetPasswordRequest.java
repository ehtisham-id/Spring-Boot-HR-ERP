package com.spring.practice1.modules.auth.dto.request;

import jakarta.validation.constraints.Size;

public record ResetPasswordRequest(@Size(min = 6, max = 6) String otp,@Size(min = 6) String password) {
}
