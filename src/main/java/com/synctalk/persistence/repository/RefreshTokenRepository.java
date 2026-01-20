package com.synctalk.persistence.repository;

import com.synctalk.persistence.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Author: Shankar Chakraborty
 * Date: 2026-01-20
 * Time: 1:55 p.m.
 */


public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findByTokenHash(String tokenHash);
}
