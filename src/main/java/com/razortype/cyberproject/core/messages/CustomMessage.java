package com.razortype.cyberproject.core.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.razortype.cyberproject.core.enums.MessageType;
import com.razortype.cyberproject.core.messages.payloads.BasePayload;
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
public class CustomMessage<T extends BasePayload> {

    @JsonProperty("message_id")
    private UUID messageId;

    private String message;

    @JsonProperty("message_type")
    private MessageType messageType;

    @JsonProperty("creation_date")
    private LocalDateTime creationDate;

    private T payload;

}
