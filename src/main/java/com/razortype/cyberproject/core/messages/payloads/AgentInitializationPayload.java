package com.razortype.cyberproject.core.messages.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AgentInitializationPayload extends BasePayload {

    @JsonProperty("agent_email")
    private String agentEmail;

    @JsonProperty("connected_at")
    private LocalDateTime connectedAt;

}
