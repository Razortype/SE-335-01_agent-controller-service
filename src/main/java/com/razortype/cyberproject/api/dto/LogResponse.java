package com.razortype.cyberproject.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.razortype.cyberproject.core.enums.LogLevel;
import com.razortype.cyberproject.core.enums.LogType;
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
public class LogResponse {

    @JsonProperty("log_id")
    private UUID id;

    @JsonProperty("log_text")
    private String logText;

    @JsonProperty("log_type")
    private LogType logType;

    @JsonProperty("log_level")
    private LogLevel logLevel;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

}
