package com.synctalk.security.auth;

/**
 * Author: Shankar Chakraborty
 * Date: 2026-01-20
 * Time: 4:20 p.m.
 */


public record TokenResponse(
        String accessToken,
        String refreshToken
) {
}
