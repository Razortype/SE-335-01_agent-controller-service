package com.razortype.cyberproject.core.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.razortype.cyberproject.core.enums.AgentMessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AgentMessage {

    @JsonProperty("message_type")
    private AgentMessageType messageType;

    private Object payload;

}
