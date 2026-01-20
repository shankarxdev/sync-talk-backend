package com.synctalk.security.jwt;

import com.synctalk.security.auth.AppUserDetailsService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.List;

/**
 * Author: Shankar Chakraborty
 * Date: 2026-01-20
 * Time: 2:09 p.m.
 */

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final AppUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            String header = request.getHeader(HttpHeaders.AUTHORIZATION);

            if (header != null && header.startsWith("Bearer ")) {
                String token = header.substring("Bearer ".length());
                Claims claims = jwtService.parse(token);

                if (jwtService.getTokenType(claims) != JwtTokenType.ACCESS) {
                    throw new IllegalArgumentException("Refresh token now allowed for APIs");
                }

                String username = claims.getSubject();
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                var authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_USER"))
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            SecurityContextHolder.clearContext();
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
    }
}
