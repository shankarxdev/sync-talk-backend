package com.synctalk.group.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Author: Shankar Chakraborty
 * Date: 2026-01-21
 * Time: 10:16 a.m.
 */


public record CreateGroupRequest(
        @NotBlank @Size(max=255) String name
) {
}
