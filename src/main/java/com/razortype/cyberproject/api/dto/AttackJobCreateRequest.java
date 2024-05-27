package com.razortype.cyberproject.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.razortype.cyberproject.core.enums.AttackType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AttackJobCreateRequest {

    @JsonProperty("attack_name")
    private String attackName;

    @JsonProperty("attack_description")
    private String attackDescription;

    @JsonProperty("attack_type")
    private AttackType attackType;

    @JsonProperty("execute_at")
    private LocalDateTime executeAt;

    @JsonProperty("agentId")
    private int agentId;

}
