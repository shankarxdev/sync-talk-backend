package com.synctalk.group.api;

import com.synctalk.common.model.ApiResponse;
import com.synctalk.group.dto.GroupChatMessage;
import com.synctalk.group.service.GroupChatQueryService;
import com.synctalk.security.auth.AppUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Author: Shankar Chakraborty
 * Date: 2026-01-21
 * Time: 11:24 a.m.
 */

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupMessageController {

    private final GroupChatQueryService chatQueryService;

    @GetMapping("/{groupId}/messages")
    public ResponseEntity<ApiResponse<List<GroupChatMessage>>> history(
            @PathVariable String groupId,
            Pageable pageable,
            @AuthenticationPrincipal AppUserDetails userDetails
    ) {
        return ResponseEntity.ok(
                ApiResponse.ok("History fetched", chatQueryService.history(groupId, userDetails.getUsername(), pageable))
        );
    }
}
