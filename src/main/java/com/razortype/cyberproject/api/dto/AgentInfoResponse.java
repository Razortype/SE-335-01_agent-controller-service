package com.razortype.cyberproject.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.razortype.cyberproject.core.enums.AgentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AgentInfoResponse {

    @JsonProperty("agent_id")
    private int id;

    @JsonProperty("agent_email")
    private String email;

    @JsonProperty("agent_current_status")
    private AgentStatus agentStatus;

    @JsonProperty("agent_ip_address")
    private String agentIpAddress;

    @JsonProperty("agent_connection_date")
    private Date connectionDate;

    @JsonProperty("execution_history")
    private List<AttackExecutionPackageResponse> executionHistory;

    private WebSocketSession session;

}
