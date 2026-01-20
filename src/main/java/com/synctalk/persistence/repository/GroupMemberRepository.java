package com.synctalk.persistence.repository;

import com.synctalk.persistence.entity.GroupMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author: Shankar Chakraborty
 * Date: 2026-01-20
 * Time: 11:14 a.m.
 */


public interface GroupMemberRepository extends JpaRepository<GroupMemberEntity, Long> {
}
