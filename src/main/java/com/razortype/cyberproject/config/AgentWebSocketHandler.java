package com.razortype.cyberproject.config;

import com.razortype.cyberproject.api.dto.AgentInfoResponse;
import com.razortype.cyberproject.core.enums.Role;
import com.razortype.cyberproject.core.utils.AuthUserUtil;
import com.razortype.cyberproject.entity.User;
import com.razortype.cyberproject.service.abstracts.AgentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.SocketException;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class AgentWebSocketHandler extends TextWebSocketHandler {

    private final AgentService agentService;
    private final AuthUserUtil authUserUtil;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        log.warn("Connected Session: " + session.getId());

        User user = authUserUtil.getAuthenticatedUser();
        if (user == null) {
            throw new SocketException("Auth user not found");
        }

        log.warn("Connected User: " + user.getEmail());

        if (user.getRole() == Role.AGENT) {
            agentService.addAgentSession(session, user);
            broadcastAgentDataToManagers();
        } else if (List.of(Role.MANAGER, Role.ADMIN).contains(user.getRole())) {
            agentService.addManager(session, user);
        }

    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        agentService.setCurrentInfo(session, message);

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        User user = authUserUtil.getAuthenticatedUser();
        if (user == null) {
            throw new SocketException("Auth user not found to be disconnect");
        }

        log.warn("Disconnected User: " + user.getEmail());

        if (user.getRole() == Role.AGENT) {
            agentService.removeAgentSession(session);
            broadcastAgentDataToManagers();
        } else if (List.of(Role.MANAGER, Role.ADMIN).contains(user.getRole())) {
            agentService.removeManager(session);
        }

    }

    private void broadcastAgentDataToManagers() {

        List<AgentInfoResponse> agentInfoResponseList = agentService.getConnectedAgentInfos();

        Map<WebSocketSession, User> managers = agentService.getConnectedManagers();
        for (Map.Entry<WebSocketSession, User> entry : managers.entrySet()) {
            messagingTemplate.convertAndSendToUser(entry.getKey().getId(), "/topic/agents", agentInfoResponseList);
        }

    }

}
