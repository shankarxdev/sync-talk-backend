package com.synctalk.security.auth;

import com.synctalk.common.model.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * Author: Shankar Chakraborty
 * Date: 2026-01-20
 * Time: 5:23 p.m.
 */

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/me")
    public ApiResponse<UserProfileResponse> me(@AuthenticationPrincipal AppUserDetails userDetails) {
        return ApiResponse.ok(
                "Profile fetched",
                userService.me(userDetails.getUsername())
        );
    }

    @PatchMapping("/me")
    public ApiResponse<UserProfileResponse> updateMe(
            @Valid @RequestBody UpdateUserProfileRequest request,
            @AuthenticationPrincipal AppUserDetails userDetails) {
        return ApiResponse.ok(
                "Profile updated",
                userService.updateMe(userDetails.getUsername(), request)
        );
    }
}
