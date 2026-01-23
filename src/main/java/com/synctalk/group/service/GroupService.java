package com.synctalk.group.service;

import com.synctalk.common.exception.UnauthorizedException;
import com.synctalk.group.dto.AddMemberRequest;
import com.synctalk.group.dto.CreateGroupRequest;
import com.synctalk.persistence.entity.GroupEntity;
import com.synctalk.persistence.entity.GroupMemberEntity;
import com.synctalk.persistence.entity.UserEntity;
import com.synctalk.persistence.repository.GroupMemberRepository;
import com.synctalk.persistence.repository.GroupRepository;
import com.synctalk.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

/**
 * Author: Shankar Chakraborty
 * Date: 2026-01-21
 * Time: 10:18 a.m.
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final UserRepository userRepository;


    @Transactional
    public GroupEntity createGroup(String creatorUsername, CreateGroupRequest request) {
        UserEntity creator = userRepository.findByUsername(creatorUsername)
                .orElseThrow(() -> new UnauthorizedException("User not found"));

        GroupEntity group = GroupEntity.builder()
                .id(UUID.randomUUID().toString())
                .name(request.name())
                .createdBy(creatorUsername)
                .createdAt(Instant.now())
                .build();
        groupRepository.save(group);

        GroupMemberEntity groupMember = GroupMemberEntity.builder()
                .groupId(group.getId())
                .userId(creator.getId())
                .role("ADMIN")
                .joinedAt(Instant.now())
                .build();
        groupMemberRepository.save(groupMember);

        return group;
    }

    @Transactional
    public void addMember(String requesterUsername, String groupId, AddMemberRequest request) {
        UserEntity requester = userRepository.findByUsername(requesterUsername)
                .orElseThrow(() -> new UnauthorizedException("User not found"));

        if (!groupMemberRepository.existsByGroupIdAndUserId(groupId, requester.getId())) {
            throw new UnauthorizedException("Not a member of this group");
        }

        UserEntity newMember = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new UnauthorizedException("User to add not found"));

        GroupMemberEntity groupMember = GroupMemberEntity.builder()
                .groupId(groupId)
                .userId(newMember.getId())
                .role("MEMBER")
                .joinedAt(Instant.now())
                .build();
        groupMemberRepository.save(groupMember);
    }
}
