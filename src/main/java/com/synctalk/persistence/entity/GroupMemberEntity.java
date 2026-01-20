package com.synctalk.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/**
 * Author: Shankar Chakraborty
 * Date: 2026-01-20
 * Time: 10:54 a.m.
 */

@Entity
@Table(name = "group_members",
        uniqueConstraints = @UniqueConstraint(columnNames = {"group_id", "user_id"}))
@Getter
@Setter
public class GroupMemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String groupId;
    private String userId;
    private String role;
    private Instant joinedAt;
}
