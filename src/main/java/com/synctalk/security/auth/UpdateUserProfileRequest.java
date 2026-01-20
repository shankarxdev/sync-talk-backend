package com.synctalk.security.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

/**
 * Author: Shankar Chakraborty
 * Date: 2026-01-20
 * Time: 4:22 p.m.
 */


public record UpdateUserProfileRequest(
        @Email String email,
        @Size(max = 255) String displayName,
        @Size(max = 1024) String avatarUrl
) {
}
