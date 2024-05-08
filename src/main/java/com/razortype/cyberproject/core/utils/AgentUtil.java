package com.razortype.cyberproject.core.utils;

import com.razortype.cyberproject.api.dto.AgentInfo;
import com.razortype.cyberproject.api.dto.AgentInfoResponse;
import com.razortype.cyberproject.api.dto.AttackExecutionPackageResponse;
import com.razortype.cyberproject.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AgentUtil {

    private final ExecutionPackageUtil executionPackageUtil;

    public List<AgentInfoResponse> convertAgentInfoResponse(
            Map<WebSocketSession, User> connectedAgents,
            Map<WebSocketSession, AgentInfo> connectedAgentInfos) {

        List<AgentInfoResponse> responses = new ArrayList<>();
        for (Map.Entry<WebSocketSession, User> entry : connectedAgents.entrySet()) {

            WebSocketSession session = entry.getKey();
            User agent = entry.getValue();
            AgentInfo agentInfo = connectedAgentInfos.get(session);

            List<AttackExecutionPackageResponse> executionPackageResponses = executionPackageUtil
                    .mapToExecutionPackageResponses(agentInfo.getExecutionHistory());

            AgentInfoResponse response = AgentInfoResponse.builder()
                    .id(agent.getId())
                    .email(agent.getEmail())
                    .agentStatus(agentInfo.getAgentStatus())
                    .agentIpAddress(agentInfo.getAgentIpAddress())
                    .connectionDate(agentInfo.getConnectionDate())
                    .executionHistory(executionPackageResponses)
                    .session(session)
                    .build();

            responses.add(response);

        }

        return responses;

    }



}
