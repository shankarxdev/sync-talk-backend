package com.synctalk.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

/**
 * Author: Shankar Chakraborty
 * Date: 2026-01-20
 * Time: 11:22 a.m.
 */

@Slf4j
@Service
public class JwtService {
    private final JwtProperties props;
    private final SecretKey key;

    public JwtService(JwtProperties props) {
        this.props = props;
        this.key = Keys.hmacShaKeyFor(props.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    public String issueAccessToken(String subject) {
        return issue(subject, JwtTokenType.ACCESS, props.getAccessTtlSeconds());
    }

    public String issueRefreshToken(String subject) {
        return issue(subject, JwtTokenType.REFRESH, props.getRefreshTtlSeconds());
    }

    public String issue(String subject, JwtTokenType type, long ttlSeconds) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(ttlSeconds);

        return Jwts.builder()
                .subject(subject)
                .claim("typ", type.name())
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .signWith(this.key)
                .compact();
    }

    public Claims parse(String token) {
        return Jwts.parser()
                .verifyWith(this.key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public JwtTokenType getTokenType(Claims claims) {
        Object v = claims.get("typ");
        if (v == null) throw new IllegalArgumentException("Missing typ claim");
        return JwtTokenType.valueOf(v.toString());
    }
}
