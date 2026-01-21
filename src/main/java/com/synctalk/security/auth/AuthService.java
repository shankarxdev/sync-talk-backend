package com.synctalk.security.auth;

import com.synctalk.common.exception.UnauthorizedException;
import com.synctalk.common.util.HashUtils;
import com.synctalk.persistence.entity.RefreshTokenEntity;
import com.synctalk.persistence.entity.UserEntity;
import com.synctalk.persistence.repository.RefreshTokenRepository;
import com.synctalk.persistence.repository.UserRepository;
import com.synctalk.security.jwt.JwtService;
import com.synctalk.security.jwt.JwtTokenType;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;


/**
 * Author: Shankar Chakraborty
 * Date: 2026-01-20
 * Time: 4:23 p.m.
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    @Transactional
    public void register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new UnauthorizedException("Username already taken");
        }

        if (userRepository.existsByEmail(request.email())) {
            throw new UnauthorizedException("Email already taken");
        }

        userRepository.save(UserEntity.builder()
                .id(UUID.randomUUID().toString())
                .username(request.username())
                .passwordHash(passwordEncoder.encode(request.password()))
                .email(request.email())
                .displayName(request.displayName())
                .avatarUrl(request.avatarUrl())
                .createdAt(Instant.now())
                .build());
    }

    @Transactional
    public TokenResponse login(LoginRequest request) {

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.username(),
                            request.password()
                    )
            );

            AppUserDetails principal = (AppUserDetails) auth.getPrincipal();
            return issueAndStoreToken(principal.user());
        } catch (Exception ex) {
            throw new UnauthorizedException("Invalid username or password");
        }
    }

    @Transactional
    public TokenResponse refresh(RefreshRequest request) {
        Claims claims;

        try {
            claims = jwtService.parse(request.refreshToken());
        } catch (Exception ex) {
            throw new UnauthorizedException("Invalid refresh token");
        }

        if (jwtService.getTokenType(claims) != JwtTokenType.REFRESH) {
            throw new UnauthorizedException("Not a refresh token");
        }

        String username = claims.getSubject();
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UnauthorizedException("Refresh token revoked"));

        String hashToken = HashUtils.sha256(request.refreshToken());

        RefreshTokenEntity storedRefreshToken = refreshTokenRepository.findByTokenHash(hashToken)
                .orElseThrow(() -> new UnauthorizedException("Refresh token revoked"));

        if (storedRefreshToken.isRevoked() || storedRefreshToken.getExpiresAt().isBefore(Instant.now())) {
            throw new UnauthorizedException("Refresh token expired/revoked");
        }

        storedRefreshToken.setRevoked(true); // rotation
        refreshTokenRepository.save(storedRefreshToken);

        return issueAndStoreToken(user);
    }


    @Transactional
    public void logout(RefreshRequest request) {
        String hashedRefreshToken = HashUtils.sha256(request.refreshToken());
        refreshTokenRepository.findByTokenHash(hashedRefreshToken).ifPresent(refreshToken -> {
            refreshToken.setRevoked(true);
            refreshTokenRepository.save(refreshToken);
        });
    }

    private TokenResponse issueAndStoreToken(UserEntity user) {
        String accessToken = jwtService.issueAccessToken(user.getUsername());
        String refreshToken = jwtService.issueRefreshToken(user.getUsername());

        Claims refreshClaims = jwtService.parse(refreshToken);

        refreshTokenRepository.save(RefreshTokenEntity.builder()
                .userId(user.getId())
                .tokenHash(HashUtils.sha256(refreshToken))
                .expiresAt(refreshClaims.getExpiration().toInstant())
                .revoked(false)
                .createdAt(Instant.now())
                .build());

        return new TokenResponse(accessToken, refreshToken);
    }

}
