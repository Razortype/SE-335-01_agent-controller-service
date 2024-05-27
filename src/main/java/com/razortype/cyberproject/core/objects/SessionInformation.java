package com.razortype.cyberproject.core.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.razortype.cyberproject.core.messages.payloads.AgentInformationPayload;
import com.razortype.cyberproject.core.messages.payloads.AgentInitializationPayload;
import com.razortype.cyberproject.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionInformation {

    @JsonProperty("session_id")
    private String sessionId;

    private String address;

    private User user;

    @JsonProperty("agent_curr_info")
    private AgentInformationPayload informationPayload;

    @JsonProperty("agent_init_info")
    private AgentInitializationPayload agentInitializationPayload;

}
