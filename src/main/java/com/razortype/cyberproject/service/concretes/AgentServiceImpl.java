package com.razortype.cyberproject.service.concretes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.razortype.cyberproject.api.dto.AgentInfo;
import com.razortype.cyberproject.api.dto.AgentInfoResponse;
import com.razortype.cyberproject.core.enums.AgentStatus;
import com.razortype.cyberproject.core.results.ErrorResult;
import com.razortype.cyberproject.core.results.Result;
import com.razortype.cyberproject.core.results.SuccessResult;
import com.razortype.cyberproject.core.utils.AgentUtil;
import com.razortype.cyberproject.core.utils.MessageUtil;
import com.razortype.cyberproject.entity.User;
import com.razortype.cyberproject.service.abstracts.AgentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AgentServiceImpl implements AgentService {

    private final AgentUtil agentUtil;
    private final MessageUtil messageUtil;

    private final HashMap<WebSocketSession, User> agents = new HashMap<>();
    private final HashMap<WebSocketSession, AgentInfo> agentConnectionInfo = new HashMap<>();

    private final HashMap<WebSocketSession, User> managers = new HashMap<>();

    public Result addAgentSession(WebSocketSession session, User user) {

        if (getConnectedAgentEmails().contains(user.getEmail())) {
            return new ErrorResult("Agent (" + user.getEmail() + ") have already connected");
        }

        agents.put(session, user);
        agentConnectionInfo.put(session,
                AgentInfo.builder()
                        .agentStatus(AgentStatus.IDLE)
                        .connectionDate(new Date())
                        .executionHistory(new ArrayList<>())
                        .agentIpAddress(session.getRemoteAddress().getHostString())
                        .build());

        Result broadcastResult = broadcastAgentInfo();
        if (!broadcastResult.isSuccess()) {
            return broadcastResult;
        }

        return new SuccessResult("Agent Connected: " + user.getEmail());
    }

    public Result removeAgentSession(WebSocketSession session) {

        if (!getConnectedAgentSessions().contains(session.getId())) {
            return new ErrorResult("Session (" + session.getAttributes().get("username") + ") not found");
        }

        agents.remove(session);
        agentConnectionInfo.remove(session);

        Result broadcastResult = broadcastAgentInfo();
        if (!broadcastResult.isSuccess()) {
            return broadcastResult;
        }

        return new SuccessResult("Agent Disconnected: " + session.getAttributes().get("username"));
    }

    @Override
    public Result setCurrentInfo(WebSocketSession session, TextMessage message) {
        // convert logic

        // agentConnectionInfo.put(session, new AgentInfo());

        broadcastAgentInfo();

        return new ErrorResult("Currently System Not Handle Info Request");
    }

    @Override
    public Map<WebSocketSession, User> getConnectedAgents() {
        return new HashMap<>(agents);
    }

    @Override
    public Result addManager(WebSocketSession session, User user) {

        if (getManagerEmails().contains(user.getEmail())) {
            return new ErrorResult("User (" + user.getEmail() + "#" + user.getRole() + ") have already connected");
        }

        managers.put(session, user);

        return new SuccessResult("User (" + user.getEmail() + "#" + user.getRole() + ") connected to agent live-information");
    }

    @Override
    public Result removeManager(WebSocketSession session) {

        if (!getManagerSessions().contains(session.getId())) {
            return new ErrorResult("Session (" + session.getAttributes().get("username") + ") not found");
        }

        managers.remove(session);

        return new SuccessResult("MANAGER/ADMIN Disconnected: " + session.getAttributes().get("username"));
    }

    @Override
    public Map<WebSocketSession, User> getConnectedManagers() {

        return new HashMap<>(managers);

    }

    @Override
    public List<AgentInfoResponse> getConnectedAgentInfos() {

        return agentUtil.convertAgentInfoResponse(
                this.agents,
                this.agentConnectionInfo);

    }

    @Override
    public Result broadcastAgentInfo() {

        String message;
        try {
            message = MessageUtil.convertToJson(getConnectedAgentInfos());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new ErrorResult("UEO: " + e.getMessage());
        }

        for (WebSocketSession session : this.managers.keySet()) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                e.printStackTrace();
                return new ErrorResult("UEO: " + e.getMessage());
            }
        }

        return new SuccessResult("Broadcast Successfully");
    }

    private List<String> getConnectedAgentSessions() {
        return agents.keySet().stream()
                .map(WebSocketSession::getId)
                .toList();
    }

    private List<String> getConnectedAgentEmails() {
        return agents.values().stream()
                .map(User::getEmail)
                .toList();
    }

    private List<String> getManagerSessions() {
        return managers.keySet().stream()
                .map(WebSocketSession::getId)
                .toList();
    }

    private List<String> getManagerEmails() {
        return managers.values().stream()
                .map(User::getEmail)
                .toList();
    }

}
