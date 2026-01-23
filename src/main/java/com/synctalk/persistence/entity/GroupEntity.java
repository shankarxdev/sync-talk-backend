package com.synctalk.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.Instant;

/**
 * Author: Shankar Chakraborty
 * Date: 2026-01-20
 * Time: 10:51 a.m.
 */


@Entity
@Table(name = "groups")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupEntity {

    @Id
    private String id;

    private String name;
    private String createdBy;
    private Instant createdAt;
}
