package com.razortype.cyberproject.core.messages.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.razortype.cyberproject.core.objects.SessionInformation;
import com.razortype.cyberproject.core.objects.dto.AgentSessionInformationResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ManagerAgentInformationPayload extends BasePayload {

    @JsonProperty("conn_agent_amount")
    private int connectedAgentAmount;

    @JsonProperty("last_update_date")
    private LocalDateTime lastUpdateDate;

    @JsonProperty("agent_session_information")
    private List<AgentSessionInformationResponse> agentSessionInformationResponses;

}
