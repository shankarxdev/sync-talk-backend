package com.synctalk.security.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Author: Shankar Chakraborty
 * Date: 2026-01-20
 * Time: 4:14 p.m.
 */


public record RegisterRequest(
        @NotBlank @Size(min = 3, max = 64) String username,
        @NotBlank @Size(min = 8, max = 200) String password,
        @Email String email,
        @Size(max = 256) String displayName,
        @Size(max = 256) String avatarUrl
) {
}
