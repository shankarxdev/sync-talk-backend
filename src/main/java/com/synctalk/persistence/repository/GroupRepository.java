package com.synctalk.persistence.repository;

import com.synctalk.persistence.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author: Shankar Chakraborty
 * Date: 2026-01-20
 * Time: 11:10 a.m.
 */

public interface GroupRepository extends JpaRepository<GroupEntity, String> {
}
