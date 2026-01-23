package com.synctalk.persistence.repository;

import com.synctalk.persistence.entity.GroupMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Author: Shankar Chakraborty
 * Date: 2026-01-20
 * Time: 11:14 a.m.
 */


public interface GroupMemberRepository extends JpaRepository<GroupMemberEntity, Long> {
    boolean existsByGroupIdAndUserId(String groupId, String userId);
    List<GroupMemberEntity> findByGroupId(String groupId);
}
