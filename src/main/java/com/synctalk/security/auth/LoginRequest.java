package com.synctalk.security.auth;

import jakarta.validation.constraints.NotBlank;

/**
 * Author: Shankar Chakraborty
 * Date: 2026-01-20
 * Time: 4:18 p.m.
 */


public record LoginRequest(
        @NotBlank String username,
        @NotBlank String password
) {
}
