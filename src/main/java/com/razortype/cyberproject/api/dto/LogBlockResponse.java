package com.razortype.cyberproject.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogBlockResponse {

    @JsonProperty("log_block_id")
    private UUID id;

    @JsonProperty("attack_job_id")
    private UUID attackJobId;

    @JsonProperty("accepting_log")
    private boolean acceptingLog;

    @JsonProperty("warn_count")
    private int warnCount;

    @JsonProperty("error_count")
    private int errorCount;

    @JsonProperty("log_amount")
    private int logAmount;

}
