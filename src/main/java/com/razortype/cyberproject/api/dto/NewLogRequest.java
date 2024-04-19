package com.razortype.cyberproject.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.razortype.cyberproject.core.enums.LogLevel;
import com.razortype.cyberproject.core.enums.LogType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewLogRequest {

    @JsonProperty("log_text")
    private String logText;

    @JsonProperty("log_type")
    private LogType logType;

    @JsonProperty("log_level")
    private LogLevel logLevel;

}
