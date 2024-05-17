package com.razortype.cyberproject.service.abstracts;

import com.razortype.cyberproject.api.dto.AgentInfoResponse;
import com.razortype.cyberproject.entity.User;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface AgentService {

    void addAgentSession(WebSocketSession session, User user);
    void removeAgentSession(WebSocketSession session);
    void setCurrentInfo(WebSocketSession session, TextMessage message);
    Map<WebSocketSession, User> getConnectedAgents();

    void addManager(WebSocketSession session, User user);
    void removeManager(WebSocketSession session);
    Map<WebSocketSession, User> getConnectedManagers();
    List<AgentInfoResponse> getConnectedAgentInfos();
    void BroadcastAgentInfo();

}
