package com.synctalk.security.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Author: Shankar Chakraborty
 * Date: 2026-01-20
 * Time: 11:18 a.m.
 */


@ConfigurationProperties(prefix = "app.jwt")
@Getter
@Setter
public class JwtProperties {
    private String secret;
    private long accessTtlSeconds;
    private long refreshTtlSeconds;
}
