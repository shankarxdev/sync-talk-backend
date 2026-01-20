package com.synctalk.persistance.repository;

import com.synctalk.persistance.entity.ChatMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author: Shankar Chakraborty
 * Date: 2026-01-20
 * Time: 11:15 a.m.
 */


public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, Long> {
}
