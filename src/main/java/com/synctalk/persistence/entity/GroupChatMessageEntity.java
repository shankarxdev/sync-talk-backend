package com.synctalk.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

/**
 * Author: Shankar Chakraborty
 * Date: 2026-01-20
 * Time: 11:05 a.m.
 */

@Entity
@Table(name = "group_chat_messages")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupChatMessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String groupId;
    private String senderUserId;
    private String senderUsername;

    @Column(columnDefinition = "text")
    private String content;
    private Instant sentAt;
}
