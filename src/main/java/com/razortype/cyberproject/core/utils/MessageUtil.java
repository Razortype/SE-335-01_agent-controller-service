package com.razortype.cyberproject.core.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.razortype.cyberproject.core.enums.AgentStatus;
import com.razortype.cyberproject.core.enums.AttackStatus;
import com.razortype.cyberproject.core.enums.AttackType;
import com.razortype.cyberproject.core.enums.MessageType;
import com.razortype.cyberproject.core.messages.CustomMessage;
import com.razortype.cyberproject.core.messages.payloads.*;
import com.razortype.cyberproject.core.results.DataResult;
import com.razortype.cyberproject.core.results.ErrorDataResult;
import com.razortype.cyberproject.core.results.ErrorResult;
import com.razortype.cyberproject.core.results.SuccessDataResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageUtil {

    private final ObjectMapper objectMapper;

    public <T extends BasePayload> CustomMessage<T> createMessage(UUID messageId, String message, MessageType messageType, T payload) {

        payload.setPayloadId(UUID.randomUUID());

        return CustomMessage.<T>builder()
                .messageId(messageId)
                .message(message)
                .messageType(messageType)
                .creationDate(LocalDateTime.now())
                .payload(payload)
                .build();

    }

    public CustomMessage<AgentInitializationPayload> createAgentInitializationMessage(String message, AgentInitializationPayload payload) {
        return createMessage(UUID.randomUUID(), message, MessageType.AGENT_INIT_PACKAGE, payload);
    }

    public CustomMessage<AttackPayload> createAttackMessage(String message, AttackPayload payload) {
        return createMessage(UUID.randomUUID(), message, MessageType.ATTACK_PACKAGE, payload);
    }

    public CustomMessage<AttackConfirmationPayload> createAttackConfirmationMessage(String message, AttackConfirmationPayload payload) {
        return createMessage(UUID.randomUUID(), message, MessageType.ATTACK_CONF_PACKAGE, payload);
    }

    public CustomMessage<ManagerAgentInformationPayload> createManagerAgentInformationMessage(String message, ManagerAgentInformationPayload payload) {
        return createMessage(UUID.randomUUID(), message, MessageType.MANAGER_AGENT_INFO_PACKAGE, payload);
    }

    public CustomMessage<AgentInformationPayload> createAgentInformationMessage(String message, AgentInformationPayload payload) {
        return createMessage(UUID.randomUUID(), message, MessageType.AGENT_INFO_PACKAGE, payload);
    }

    private JsonNode textMessageToJsonNode(TextMessage message) {
        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(message.getPayload());
        } catch (IOException e) {
            return null;
        }
        return jsonNode;
    }

    public CustomMessage<? extends BasePayload> textMessageToCustomMessage(TextMessage textMessage) throws IOException {

        JsonNode node = textMessageToJsonNode(textMessage);

        UUID messageId;
        String message;
        MessageType messageType;
        LocalDateTime creationDate;

        try {
            messageId = UUID.fromString(node.get("message_id").asText());
            message = node.get("message").asText();
            messageType = MessageType.valueOf(node.get("message_type").asText());
            creationDate = LocalDateTime.parse(node.get("creation_date").asText());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        CustomMessage<?> customMessage = CustomMessage.builder()
                        .messageId(messageId)
                        .message(message)
                        .messageType(messageType)
                        .creationDate(creationDate)
                        .build();

        BasePayload payload = parsePayload(messageType, node.get("payload"));

        switch (messageType) {
            case AGENT_INFO_PACKAGE -> ((CustomMessage<AgentInformationPayload>) customMessage).setPayload((AgentInformationPayload) payload);
            case AGENT_INIT_PACKAGE -> ((CustomMessage<AgentInitializationPayload>)customMessage).setPayload((AgentInitializationPayload) payload);
            case ATTACK_PACKAGE -> ((CustomMessage<AttackPayload>) customMessage).setPayload((AttackPayload) payload);
            case ATTACK_CONF_PACKAGE -> ((CustomMessage<AttackConfirmationPayload>) customMessage).setPayload((AttackConfirmationPayload) payload);
            default -> throw new RuntimeException("Not allowed package transfer: " + messageType.name());
        }

        return customMessage;
    }

    public BasePayload parsePayload(MessageType messageType, JsonNode payloadNode) {

        if (payloadNode.get("payload_id") == null) {
            return null;
        }

        BasePayload payload = null;
        try {
            UUID payloadId = UUID.fromString(payloadNode.get("payload_id").asText());
            switch (messageType) {
                case AGENT_INFO_PACKAGE -> {

                    List<AttackPayload> executionHistory = new ArrayList<>();
                    for (JsonNode historyNode : payloadNode.get("execution_history")) {
                        AttackPayload attackPayload = (AttackPayload) parsePayload(MessageType.ATTACK_PACKAGE, historyNode);
                        executionHistory.add(attackPayload);
                    }

                    payload = AgentInformationPayload.builder()
                            .payloadId(payloadId)
                            .agentStatus(AgentStatus.valueOf(payloadNode.get("agent_status").asText()))
                            .executingAttackPayload((AttackPayload) parsePayload(MessageType.ATTACK_PACKAGE, payloadNode.get("executing_attack")))
                            .amountOfQueuedAttack(payloadNode.get("amount_of_queued_attack").asInt())
                            .usingToken(payloadNode.get("using_token").asText())
                            .executionHistory(executionHistory)
                            .build();
                }
                case AGENT_INIT_PACKAGE -> {
                    payload = AgentInitializationPayload.builder()
                            .payloadId(payloadId)
                            .agentEmail(payloadNode.get("agent_email").asText())
                            .connectedAt(LocalDateTime.parse(payloadNode.get("connected_at").asText()))
                            .build();
                }
                case ATTACK_PACKAGE -> {
                    payload = AttackPayload.builder()
                            .payloadId(payloadId)
                            .attackJobId(UUID.fromString(payloadNode.get("attack_job_id").asText()))
                            .logBlockId(UUID.fromString(payloadNode.get("log_block_id").asText()))
                            .attackName(payloadNode.get("attack_name").asText())
                            .attackDescription(payloadNode.get("attack_description").asText())
                            .attackType(AttackType.valueOf(payloadNode.get("attack_type").asText()))
                            .executedAt(LocalDateTime.parse(payloadNode.get("executed_at").asText()))
                            .build();
                }
                case ATTACK_CONF_PACKAGE -> {

                    payload = AttackConfirmationPayload.builder()
                            .payloadId(payloadId)
                            .attackJobId(UUID.fromString(payloadNode.get("attack_job_id").asText()))
                            .attackStatus(AttackStatus.valueOf(payloadNode.get("attack_status").asText()))
                            .attackType(AttackType.valueOf(payloadNode.get("attack_type").asText()))
                            .startExecutingAt(LocalDateTime.parse(payloadNode.get("start_executing_at").asText()))
                            .build();
                }
                default -> throw new RuntimeException("Not allowed package transfer: " + messageType.name());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return payload;
    }

    public String customMessageToJson(CustomMessage<? extends BasePayload> customMessage) throws JsonProcessingException {
        return objectMapper.writeValueAsString(customMessage);
    }

    public DataResult<CustomMessage<?>> getCustomMessageFromSocketTextMessage(TextMessage message) {

        CustomMessage<?> customMessage;
        try {
            customMessage = textMessageToCustomMessage(message);
        } catch (IOException e) {
            return new ErrorDataResult<>("Converting error: " + e.getMessage());
        } catch (Exception e) {
            return new ErrorDataResult<>("UEO: " + e.getMessage());
        }

        return new SuccessDataResult(customMessage, "Message converted");
    }

}
