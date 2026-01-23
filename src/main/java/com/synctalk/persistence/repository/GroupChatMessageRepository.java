package com.synctalk.persistence.repository;

import com.synctalk.persistence.entity.GroupChatMessageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



/**
 * Author: Shankar Chakraborty
 * Date: 2026-01-20
 * Time: 11:15 a.m.
 */


public interface GroupChatMessageRepository extends JpaRepository<GroupChatMessageEntity, Long> {
    Page<GroupChatMessageEntity> findByGroupIdOrderBySentAtDesc(String groupId, Pageable pageable);
}
