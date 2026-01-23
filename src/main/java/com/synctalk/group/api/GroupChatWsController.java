package com.synctalk.group.api;

import com.synctalk.group.dto.GroupChatMessage;
import com.synctalk.group.dto.SendGroupMessageRequest;
import com.synctalk.group.service.GroupChatCommandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;

/**
 * Author: Shankar Chakraborty
 * Date: 2026-01-21
 * Time: 11:26 a.m.
 */

@Controller
@RequiredArgsConstructor
public class GroupChatWsController {

    private final GroupChatCommandService chatCommandService;

    @MessageMapping("/groups/{groupId}/send")
    public GroupChatMessage send(
            @DestinationVariable String groupId,
            @Valid SendGroupMessageRequest request,
            Principal principal
    ) {
        return chatCommandService.sendGroupMessage(groupId, principal.getName(), request.content());
    }
}
