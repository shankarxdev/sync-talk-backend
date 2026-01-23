package com.synctalk.group.service;

import com.synctalk.common.exception.UnauthorizedException;
import com.synctalk.persistence.entity.UserEntity;
import com.synctalk.persistence.repository.GroupMemberRepository;
import com.synctalk.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Author: Shankar Chakraborty
 * Date: 2026-01-21
 * Time: 10:48 a.m.
 */

@Service
@RequiredArgsConstructor
public class GroupChatAuthorizationService {

    private final GroupMemberRepository groupMemberRepository;
    private final UserRepository userRepository;

    public UserEntity requireUser(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UnauthorizedException("User not found"));
    }

    public void assertMember(String groupId, String userId) {
        if (!groupMemberRepository.existsByGroupIdAndUserId(groupId, userId)) {
            throw new UnauthorizedException("Not a member of this group");
        }
    }
}
