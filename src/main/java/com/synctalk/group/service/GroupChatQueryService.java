package com.synctalk.group.service;

import com.synctalk.group.dto.GroupChatMessage;
import com.synctalk.persistence.entity.UserEntity;
import com.synctalk.persistence.repository.GroupChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author: Shankar Chakraborty
 * Date: 2026-01-21
 * Time: 11:10 a.m.
 */


@Service
@RequiredArgsConstructor
public class GroupChatQueryService {

    private final GroupChatAuthorizationService authorizationService;
    private final GroupChatMessageRepository messageRepository;


    @Transactional
    public List<GroupChatMessage> history(String groupId, String username, Pageable pageable) {
        UserEntity user = authorizationService.requireUser(username);
        authorizationService.assertMember(groupId, user.getId());



        return messageRepository.findByGroupIdOrderBySentAtDesc(groupId, pageable)
                .map(message -> GroupChatMessage.builder()
                        .id(message.getId())
                        .groupId(message.getGroupId())
                        .senderUserId(message.getSenderUserId())
                        .senderUsername(message.getSenderUsername())
                        .content(message.getContent())
                        .sentAt(message.getSentAt())
                        .build()
                )
                .getContent();
    }


}
