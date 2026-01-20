package com.synctalk.security.auth;

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
    public UserProfileResponse me(@AuthenticationPrincipal AppUserDetails userDetails) {
        return userService.me(userDetails.getUsername());
    }

    @PatchMapping("/me")
    public UserProfileResponse updateMe(@Valid @RequestBody UpdateUserProfileRequest request,
                                        @AuthenticationPrincipal AppUserDetails userDetails) {
        return userService.updateMe(userDetails.getUsername(), request);
    }
}
