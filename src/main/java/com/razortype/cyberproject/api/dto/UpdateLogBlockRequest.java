package com.razortype.cyberproject.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateLogBlockRequest {

    @JsonProperty("accepting_log")
    private boolean acceptingLog;

}
