package com.synctalk.persistance.repository;

import com.synctalk.persistance.entity.GroupMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author: Shankar Chakraborty
 * Date: 2026-01-20
 * Time: 11:14 a.m.
 */


public interface GroupMemberRepository extends JpaRepository<GroupMemberEntity, Long> {
}
