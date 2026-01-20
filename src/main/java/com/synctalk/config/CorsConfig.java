package com.synctalk.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Author: Shankar Chakraborty
 * Date: 2026-01-19
 * Time: 11:09 p.m.
 */


@Slf4j
@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cors = new CorsConfiguration();

        // IMPORTANT: Use your real frontend origins here.
        cors.setAllowedOriginPatterns(List.of(
                "http://localhost:3000"
        ));

        cors.setAllowedMethods(List.of(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.PATCH.name(),
                HttpMethod.DELETE.name(),
                HttpMethod.OPTIONS.name()
        ));

        cors.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        cors.setExposedHeaders(List.of("Authorization"));

        // For JWT in Authorization header (no cookies), you can keep this false.
        // Set true ONLY if you use cookies (session auth / refresh token cookie / CSRF).
        cors.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // Apply to your API routes (or "/**" if you prefer).
        source.registerCorsConfiguration("/api/**", cors);
        return source;
    }
}
