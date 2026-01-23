package com.synctalk.group.api;

import com.synctalk.common.model.ApiResponse;
import com.synctalk.group.dto.AddMemberRequest;
import com.synctalk.group.dto.CreateGroupRequest;
import com.synctalk.group.service.GroupService;
import com.synctalk.persistence.entity.GroupEntity;
import com.synctalk.security.auth.AppUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * Author: Shankar Chakraborty
 * Date: 2026-01-21
 * Time: 10:36 a.m.
 */

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    public ResponseEntity<ApiResponse<GroupEntity>> create(
            @Valid @RequestBody CreateGroupRequest request,
            @AuthenticationPrincipal AppUserDetails userDetails
    ) {
        GroupEntity group = groupService.createGroup(userDetails.getUsername(), request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Group created", group));
    }

    @PostMapping("/{groupId}/members")
    public ResponseEntity<ApiResponse<Void>> addMember(
            @PathVariable String groupId,
            @Valid @RequestBody AddMemberRequest request,
            @AuthenticationPrincipal AppUserDetails userDetails
    ) {
        groupService.addMember(userDetails.getUsername(), groupId, request);
        return ResponseEntity.ok(ApiResponse.ok("Member added"));
    }
}
