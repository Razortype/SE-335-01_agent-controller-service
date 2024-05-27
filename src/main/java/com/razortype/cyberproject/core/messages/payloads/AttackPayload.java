package com.razortype.cyberproject.core.messages.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.razortype.cyberproject.core.enums.AttackType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AttackPayload extends BasePayload {

    @JsonProperty("attack_job_id")
    private UUID attackJobId;

    @JsonProperty("log_block_id")
    private UUID logBlockId;

    @JsonProperty("attack_name")
    private String attackName;

    @JsonProperty("attack_description")
    private String attackDescription;

    @JsonProperty("attack_type")
    private AttackType attackType;

    @JsonProperty("executed_at")
    private LocalDateTime executedAt;

}
