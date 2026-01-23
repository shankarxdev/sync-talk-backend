package com.synctalk.group.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Author: Shankar Chakraborty
 * Date: 2026-01-21
 * Time: 10:17 a.m.
 */


public record AddMemberRequest(
        @NotBlank String username
) {
}
