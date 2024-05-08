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
public class AttackExecutionPackage {

    @JsonProperty("attack_name")
    private String attackName;

    @JsonProperty("attack_type")
    private AttackType attackType;

    @JsonProperty("executed_at")
    private LocalDateTime executedAt;

    @JsonProperty("attack_log_id")
    private String attackLogId;

}
