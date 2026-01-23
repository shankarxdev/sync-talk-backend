package com.synctalk.group.service;

import com.synctalk.group.dto.GroupChatMessage;
import com.synctalk.persistence.entity.GroupChatMessageEntity;
import com.synctalk.persistence.entity.UserEntity;
import com.synctalk.persistence.repository.GroupChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

/**
 * Author: Shankar Chakraborty
 * Date: 2026-01-21
 * Time: 10:55 a.m.
 */


@Service
@RequiredArgsConstructor
public class GroupChatCommandService {

    private final GroupChatMessageRepository messageRepository;
    private final GroupChatAuthorizationService authorizationService;
    private final SimpMessagingTemplate broker;

    @Transactional
    public GroupChatMessage sendGroupMessage(String groupId, String senderUsername, String content) {
        UserEntity sender = authorizationService.requireUser(senderUsername);
        authorizationService.assertMember(groupId, sender.getId());

        GroupChatMessageEntity chatMessage = GroupChatMessageEntity.builder()
                .groupId(groupId)
                .senderUserId(sender.getId())
                .senderUsername(sender.getUsername())
                .content(content)
                .sentAt(Instant.now())
                .build();

        GroupChatMessageEntity savedMessage = messageRepository.save(chatMessage);

        GroupChatMessage message = GroupChatMessage.builder()
                .id(savedMessage.getId())
                .groupId(savedMessage.getGroupId())
                .senderUserId(savedMessage.getSenderUserId())
                .senderUsername(sender.getUsername())
                .content(savedMessage.getContent())
                .sentAt(savedMessage.getSentAt())
                .build();

        broker.convertAndSend("/topic/groups/" + groupId, message);
        return message;
    }

}
