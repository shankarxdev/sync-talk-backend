package com.synctalk.persistance.repository;

import com.synctalk.persistance.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Author: Shankar Chakraborty
 * Date: 2026-01-20
 * Time: 1:53 p.m.
 */


public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByUsername(String username);
    boolean existsByUsername(String username);
}
