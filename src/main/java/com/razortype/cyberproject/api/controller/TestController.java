package com.razortype.cyberproject.api.controller;

import com.razortype.cyberproject.core.enums.AttackType;
import com.razortype.cyberproject.core.enums.Role;
import com.razortype.cyberproject.core.messages.payloads.AttackPayload;
import com.razortype.cyberproject.core.objects.SessionInformation;
import com.razortype.cyberproject.core.utils.MessageUtil;
import com.razortype.cyberproject.service.abstracts.SocketSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/test")
@RequiredArgsConstructor
public class TestController {

    private final SocketSessionService socketSessionService;
    private final MessageUtil messageUtil;

    @PostMapping("/attack-job")
    public ResponseEntity<?> sendAttackJobTest() throws IOException {

        HashMap<WebSocketSession, SessionInformation> infoMap = socketSessionService.getConnectedSessionInformation();

        WebSocketSession session = null;
        SessionInformation info = null;
        boolean isFound = false;

        for (Map.Entry<WebSocketSession, SessionInformation> entry: infoMap.entrySet()) {
            session = entry.getKey();
            info = entry.getValue();
            if (info.getUser().getRole() == Role.AGENT && info.getUser().getEmail().equals("agent_1@agent.com")) {
                isFound = true;
                break;
            }
        }

        if (!isFound) {
            return ResponseEntity.badRequest().body("Agent not found");
        }

        session.sendMessage(new TextMessage(messageUtil.customMessageToJson(messageUtil.createAttackMessage(
                        "Test Controller Message",
                        AttackPayload.builder()
                                .payloadId(UUID.randomUUID())
                                .attackJobId(UUID.fromString("3b30295c-7814-4624-bfef-f748d6d3f251"))
                                .logBlockId(UUID.fromString("9f6a3258-7868-4fd4-a236-b31dc3b30373"))
                                .attackName("Custom Attack # 1 || TESTING!!.")
                                .attackDescription("Attack Description :: EX'")
                                .attackType(AttackType.COOKIE_DISCOVERY)
                                .executedAt(LocalDateTime.now())
                                .build()))));

        return ResponseEntity.ok("Message Sent");

    }

}
