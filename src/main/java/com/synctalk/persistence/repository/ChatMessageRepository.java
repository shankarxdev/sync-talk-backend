package com.synctalk.persistence.repository;

import com.synctalk.persistence.entity.ChatMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author: Shankar Chakraborty
 * Date: 2026-01-20
 * Time: 11:15 a.m.
 */


public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, Long> {
}
