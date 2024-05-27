package com.razortype.cyberproject.core.messages.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.razortype.cyberproject.core.enums.AgentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AgentInformationPayload extends BasePayload {

    @JsonProperty("agent_status")
    private AgentStatus agentStatus;

    @JsonProperty("executing_attack")
    private AttackPayload executingAttackPayload;

    @JsonProperty("amount_of_queued_attack")
    private int amountOfQueuedAttack;

    @JsonProperty("using_token")
    private String usingToken;

    @JsonProperty("execution_history")
    private List<AttackPayload> executionHistory;

}
