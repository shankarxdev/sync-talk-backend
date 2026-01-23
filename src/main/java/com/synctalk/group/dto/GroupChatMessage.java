package com.synctalk.group.dto;

import lombok.Builder;

import java.time.Instant;

/**
 * Author: Shankar Chakraborty
 * Date: 2026-01-21
 * Time: 10:59 a.m.
 */


@Builder
public record GroupChatMessage(
        long id,
        String groupId,
        String senderUserId,
        String senderUsername,
        String content,
        Instant sentAt
) {
}
