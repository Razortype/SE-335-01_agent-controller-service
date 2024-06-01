package com.razortype.cyberproject.core.messages.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.razortype.cyberproject.core.enums.AttackStatus;
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
public class AttackConfirmationPayload extends BasePayload {

    @JsonProperty("attack_job_id")
    private UUID attackJobId;

    @JsonProperty("attack_status")
    private AttackStatus attackStatus;

    @JsonProperty("attack_type")
    private AttackType attackType;

    @JsonProperty("start_executing_at")
    private LocalDateTime startExecutingAt;

}
