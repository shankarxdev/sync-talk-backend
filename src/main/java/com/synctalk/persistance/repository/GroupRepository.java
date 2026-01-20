package com.synctalk.persistance.repository;

import com.synctalk.persistance.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author: Shankar Chakraborty
 * Date: 2026-01-20
 * Time: 11:10 a.m.
 */

public interface GroupRepository extends JpaRepository<GroupEntity, String> {
}
