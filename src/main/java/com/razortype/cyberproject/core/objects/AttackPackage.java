package com.razortype.cyberproject.core.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.razortype.cyberproject.core.enums.AttackType;
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
public class AttackPackage {

    @JsonProperty("attack_job_id")
    private UUID attackJobId;

    @JsonProperty("attack_name")
    private String attackName;

    @JsonProperty("attack_description")
    private String attackDescription;

    @JsonProperty("attack_type")
    private AttackType attackType;

    @JsonProperty("executed_at")
    private LocalDateTime executedAt;

    @JsonProperty("log_block_id")
    private UUID localBlockId;

}
