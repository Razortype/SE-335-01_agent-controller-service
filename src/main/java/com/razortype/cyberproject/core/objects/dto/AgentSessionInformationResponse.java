package com.razortype.cyberproject.core.objects.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.razortype.cyberproject.core.enums.AgentStatus;
import com.razortype.cyberproject.core.messages.payloads.AttackPayload;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AgentSessionInformationResponse {

    @JsonProperty("session_id")
    private String sessionId;

    private String address;

    @JsonProperty("agent_id")
    private int agentId;

    @JsonProperty("agent_email")
    private String agentEmail;

    @JsonProperty("agent_status")
    private AgentStatus agentStatus;

    @JsonProperty("using_token")
    private String usingToken;

    @JsonProperty("executing_attack")
    private AttackPayload executingAttackPayload;

    @JsonProperty("execution_history")
    private List<AttackPayload> executionHistory;

    @JsonProperty("connected_at")
    private LocalDateTime connectedAt;

}
