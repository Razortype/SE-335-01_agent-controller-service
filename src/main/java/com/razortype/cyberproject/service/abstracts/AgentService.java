package com.razortype.cyberproject.service.abstracts;

import com.razortype.cyberproject.api.dto.AgentInfoResponse;
import com.razortype.cyberproject.core.results.Result;
import com.razortype.cyberproject.entity.User;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface AgentService {

    Result addAgentSession(WebSocketSession session, User user);
    Result removeAgentSession(WebSocketSession session);
    Result setCurrentInfo(WebSocketSession session, TextMessage message);
    Map<WebSocketSession, User> getConnectedAgents();

    Result addManager(WebSocketSession session, User user);
    Result removeManager(WebSocketSession session);
    Map<WebSocketSession, User> getConnectedManagers();
    List<AgentInfoResponse> getConnectedAgentInfos();
    Result broadcastAgentInfo();

}
