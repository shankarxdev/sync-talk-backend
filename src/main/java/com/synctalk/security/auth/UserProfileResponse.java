package com.synctalk.security.auth;

import lombok.Builder;

/**
 * Author: Shankar Chakraborty
 * Date: 2026-01-20
 * Time: 4:21 p.m.
 */

@Builder
public record UserProfileResponse(
        String id,
        String username,
        String email,
        String displayName,
        String avatarUrl
) {
}
