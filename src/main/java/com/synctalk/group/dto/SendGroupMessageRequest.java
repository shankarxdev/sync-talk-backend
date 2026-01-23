package com.synctalk.group.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Author: Shankar Chakraborty
 * Date: 2026-01-21
 * Time: 10:47 a.m.
 */


public record SendGroupMessageRequest(
        @NotBlank String content
) {
}
