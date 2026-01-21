package com.synctalk.persistence.repository;

import com.synctalk.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Author: Shankar Chakraborty
 * Date: 2026-01-20
 * Time: 1:53 p.m.
 */


public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);


    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
