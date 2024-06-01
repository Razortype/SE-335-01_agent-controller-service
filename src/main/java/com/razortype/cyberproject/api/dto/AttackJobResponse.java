package com.razortype.cyberproject.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.razortype.cyberproject.core.enums.AttackType;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttackJobResponse {

    private UUID id;

    @JsonProperty("attack_name")
    private String attackName;

    @Column(name = "attack_description")
    private String attackDescription;

    @JsonProperty("attack_type")
    private AttackType attackType;

    @Column(name = "is_active")
    private boolean active;

    @Column(name = "is_crashed")
    private boolean crashed;

    @Column(name = "is_started")
    private boolean started;

    @Column(name = "executed_at")
    private LocalDateTime executeAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "start_executing_at")
    private LocalDateTime startExecutingAt;

    @Column(name = "agent_id")
    private int agentId;

    @Column(name = "log_block_id")
    private UUID logBlockId;

}
