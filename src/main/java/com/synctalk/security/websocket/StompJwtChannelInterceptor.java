package com.synctalk.security.websocket;

import com.synctalk.persistence.entity.UserEntity;
import com.synctalk.persistence.repository.UserRepository;
import com.synctalk.security.jwt.JwtService;
import com.synctalk.security.jwt.JwtTokenType;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Author: Shankar Chakraborty
 * Date: 2026-01-21
 * Time: 9:54 a.m.
 */
@Component
@RequiredArgsConstructor
public class StompJwtChannelInterceptor implements ChannelInterceptor {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public @Nullable Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            List<String> authHeaders = accessor.getNativeHeader("Authorization");

            if (authHeaders == null || authHeaders.isEmpty()) {
                throw new IllegalArgumentException("Missing Authorization header");
            }

            String header = authHeaders.getFirst();
            if (header == null || !header.startsWith("Bearer ")) {
                throw new IllegalArgumentException("Missing Authorization header");
            }

            String token = header.substring("Bearer ".length());
            Claims claims = jwtService.parse(token);

            if (jwtService.getTokenType(claims) != JwtTokenType.ACCESS) {
                throw new IllegalArgumentException("Refresh token not allowed for WebSocket");
            }

            String username = claims.getSubject();

            UserEntity user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            accessor.setUser(new WebSockerPrinciple(user.getUsername()));
        }

        return message;
    }
}
