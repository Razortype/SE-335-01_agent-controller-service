package com.razortype.cyberproject.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.razortype.cyberproject.core.enums.AttackType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttackJobCreateRequest {

    @JsonProperty("attack_name")
    private String attackName;

    @JsonProperty("attack_description")
    private String attackDescription;

    @JsonProperty("attack_type")
    private AttackType attackType;

    @JsonProperty("execute_at")
    private LocalDateTime executeAt;

    @JsonProperty("agent_id")
    private int agentId;

}
