package com.razortype.cyberproject.service.concretes;

import com.razortype.cyberproject.api.dto.AgentInfo;
import com.razortype.cyberproject.api.dto.AgentInfoResponse;
import com.razortype.cyberproject.core.utils.AgentUtil;
import com.razortype.cyberproject.entity.User;
import com.razortype.cyberproject.service.abstracts.AgentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AgentServiceImpl implements AgentService {

    private final AgentUtil agentUtil;

    private final HashMap<WebSocketSession, User> agents = new HashMap<>();
    private final HashMap<WebSocketSession, AgentInfo> agentConnectionInfo = new HashMap<>();

    private final HashMap<WebSocketSession, User> managers = new HashMap<>();

    public void addAgentSession(WebSocketSession session, User user) {
        agents.put(session, user);
    }

    public void removeAgentSession(WebSocketSession session) {
        agents.remove(session);
        agentConnectionInfo.remove(session);
    }

    @Override
    public void setCurrentInfo(WebSocketSession session, TextMessage message) {
        // convert logic
        agentConnectionInfo.put(session, new AgentInfo());
    }

    @Override
    public Map<WebSocketSession, User> getConnectedAgents() {
        return new HashMap<>(agents);
    }

    @Override
    public void addManager(WebSocketSession session, User user) {

        managers.put(session, user);

    }

    @Override
    public void removeManager(WebSocketSession session) {

        managers.remove(session);

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
    public void BroadcastAgentInfo() {
        // broadcast logic
    }


}
