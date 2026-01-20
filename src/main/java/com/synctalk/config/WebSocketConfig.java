package com.synctalk.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Author: Shankar Chakraborty
 * Date: 2026-01-10
 * Time: 11:35 p.m.
 */

@Slf4j
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")                // WebSocket handshake endpoint
                .setAllowedOriginPatterns("http://localhost:3000");
        // .withSockJS(); // optional
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");   // SEND -> @MessageMapping
        registry.enableSimpleBroker("/topic", "/queue");      // SUBSCRIBE destinations
        registry.setUserDestinationPrefix("/user");           // private inbox prefix
    }
}
