package com.synctalk.security.auth;

import com.synctalk.common.exception.UnauthorizedException;
import com.synctalk.persistence.entity.UserEntity;
import com.synctalk.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Author: Shankar Chakraborty
 * Date: 2026-01-20
 * Time: 5:16 p.m.
 */

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    @Transactional
    public UserProfileResponse me(String username) {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UnauthorizedException("User not found"));

        return mapUserResponse(userEntity);
    }

    @Transactional
    public UserProfileResponse updateMe(String username, UpdateUserProfileRequest req) {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UnauthorizedException("User not found"));

        if (req.email() != null) userEntity.setEmail(req.email());
        if (req.displayName() != null) userEntity.setDisplayName(req.displayName());
        if (req.avatarUrl() != null) userEntity.setAvatarUrl(req.avatarUrl());

        userRepository.save(userEntity);
        return mapUserResponse(userEntity);
    }

    private UserProfileResponse mapUserResponse(UserEntity user) {
        return UserProfileResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .displayName(user.getDisplayName())
                .avatarUrl(user.getAvatarUrl())
                .build();
    }
}
