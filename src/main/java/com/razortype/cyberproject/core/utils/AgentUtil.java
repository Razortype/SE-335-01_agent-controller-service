package com.razortype.cyberproject.core.utils;

import com.razortype.cyberproject.api.dto.AgentInfo;
import com.razortype.cyberproject.api.dto.AgentInfoResponse;
import com.razortype.cyberproject.api.dto.AttackExecutionPackageResponse;
import com.razortype.cyberproject.core.results.DataResult;
import com.razortype.cyberproject.entity.User;
import com.razortype.cyberproject.service.abstracts.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AgentUtil {

    private final ExecutionPackageUtil executionPackageUtil;
    private final UserService userService;

    public List<AgentInfoResponse> convertAgentInfoResponse(
            Map<WebSocketSession, User> connectedAgents,
            Map<WebSocketSession, AgentInfo> connectedAgentInfos) {

        List<AgentInfoResponse> responses = new ArrayList<>();
        for (WebSocketSession session: connectedAgents.keySet()) {

            User agent = connectedAgents.get(session);
            AgentInfo info = connectedAgentInfos.get(session);

            List<AttackExecutionPackageResponse> attackResponse = executionPackageUtil
                    .mapToExecutionPackageResponses(info.getExecutionHistory());

            responses.add(AgentInfoResponse.builder()
                    .id(agent.getId())
                    .email(agent.getEmail())
                    .agentStatus(info.getAgentStatus())
                    .agentIpAddress(info.getAgentIpAddress())
                    .connectionDate(info.getConnectionDate())
                    .executionHistory(attackResponse)
                    .sessionId(session.getId())
                    .build());
        }

        return responses;

    }

    public User checkAndGetSessionAuth(WebSocketSession session) throws IOException {
        String username = (String) session.getAttributes().get("username");
        if (username == null) {
            session.sendMessage(new TextMessage("Session requires authentication"));
            session.close();
            return null;
        }
        DataResult userResult = userService.getUserByEmail(username);
        if (!userResult.isSuccess()) {
            session.sendMessage(new TextMessage(userResult.getMessage()));
            session.close();
            return null;
        }
        return (User) userResult.getData();
    }

}
