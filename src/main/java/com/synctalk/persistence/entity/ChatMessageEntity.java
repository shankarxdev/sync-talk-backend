package com.synctalk.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/**
 * Author: Shankar Chakraborty
 * Date: 2026-01-20
 * Time: 11:05 a.m.
 */

@Entity
@Table(name = "chat_messages")
@Getter
@Setter
public class ChatMessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String groupId;
    private String senderUserId;

    @Column(columnDefinition = "text")
    private String content;
    private Instant sentAt;
}
