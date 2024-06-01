package com.razortype.cyberproject.service.concretes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.razortype.cyberproject.core.enums.AgentStatus;
import com.razortype.cyberproject.core.enums.MessageType;
import com.razortype.cyberproject.core.enums.Role;
import com.razortype.cyberproject.core.messages.CustomMessage;
import com.razortype.cyberproject.core.messages.payloads.AgentInformationPayload;
import com.razortype.cyberproject.core.messages.payloads.AgentInitializationPayload;
import com.razortype.cyberproject.core.messages.payloads.AttackConfirmationPayload;
import com.razortype.cyberproject.core.messages.payloads.ManagerAgentInformationPayload;
import com.razortype.cyberproject.core.objects.SessionInformation;
import com.razortype.cyberproject.core.objects.dto.AgentSessionInformationResponse;
import com.razortype.cyberproject.core.results.DataResult;
import com.razortype.cyberproject.core.results.ErrorResult;
import com.razortype.cyberproject.core.results.Result;
import com.razortype.cyberproject.core.results.SuccessResult;
import com.razortype.cyberproject.core.utils.SocketSessionUtil;
import com.razortype.cyberproject.core.utils.MessageUtil;
import com.razortype.cyberproject.entity.AttackJob;
import com.razortype.cyberproject.entity.User;
import com.razortype.cyberproject.service.abstracts.AttackJobService;
import com.razortype.cyberproject.service.abstracts.SocketSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SocketSessionServiceImpl implements SocketSessionService {

    private final SocketSessionUtil socketSessionUtil;
    private final AttackJobService attackJobService;
    private final MessageUtil messageUtil;

    private final HashMap<WebSocketSession, SessionInformation> connectedSessionInformation = new HashMap<>();

    public Result addAgentSession(WebSocketSession session, User user) {

        if (isSessionEmailExists(user.getEmail(), Arrays.asList(Role.AGENT))) {
            return new ErrorResult("Agent (" + user.getEmail() + ") have already connected");
        }

        connectedSessionInformation.put(session, SessionInformation.builder()
                .sessionId(session.getId())
                .user(user)
                .informationPayload(new AgentInformationPayload())
                .agentInitializationPayload(new AgentInitializationPayload())
                .build());

        broadcastAgentInfo();

        return new SuccessResult("Agent Connected: " + user.getEmail());
    }

    public Result removeAgentSession(WebSocketSession session) {

        if (!isSessionExists(session)) {
            return new ErrorResult("Session (" + session.getAttributes().get("username") + ") not found");
        }

        connectedSessionInformation.remove(session);

        broadcastAgentInfo();

        return new SuccessResult("Agent Disconnected: " + session.getAttributes().get("username"));
    }

    @Override
    public Result handleReceivedMessage(WebSocketSession session, TextMessage message) {

        if (!isSessionExists(session)) {
            return new ErrorResult("Session (" + session.getAttributes().get("username") + ") not found");
        }

        DataResult<CustomMessage<?>> customMessageResult = messageUtil.getCustomMessageFromSocketTextMessage(message);
        if (!customMessageResult.isSuccess()) {
            return new ErrorResult(customMessageResult.getMessage());
        }
        CustomMessage<?> customMessage = customMessageResult.getData();

        switch (customMessage.getMessageType()) {
            case AGENT_INFO_PACKAGE -> connectedSessionInformation.get(session)
                        .setInformationPayload((AgentInformationPayload) customMessage.getPayload());
            case AGENT_INIT_PACKAGE -> {
                SessionInformation info = connectedSessionInformation.get(session);
                info.setAddress(session.getRemoteAddress().getHostName());
                info.setAgentInitializationPayload((AgentInitializationPayload) customMessage.getPayload());
            }
            case ATTACK_CONF_PACKAGE -> {

                AttackConfirmationPayload payload = (AttackConfirmationPayload) customMessage.getPayload();

                DataResult<AttackJob> attackJobDataResult = attackJobService
                        .getAttackJobById(payload.getAttackJobId());
                if (!attackJobDataResult.isSuccess()) {
                    return new ErrorResult(attackJobDataResult.getMessage());
                }

                AttackJob attackJob = attackJobDataResult.getData();
                attackJob.setActive(true);
                attackJob.setStarted(true);
                attackJob.setStartExecutingAt(payload.getStartExecutingAt());

                Result saveResult = attackJobService.save(attackJob);
                if (!saveResult.isSuccess()) {
                    return saveResult;
                }

            }

            default -> System.out.println("Message not handled: " + message.getPayload());

        }

        broadcastAgentInfo();
        return new SuccessResult("Message handled");

    }

    @Override
    public Result addManager(WebSocketSession session, User user) {

        if (isSessionEmailExists(user.getEmail(), Arrays.asList(Role.MANAGER, Role.ADMIN))) {
            return new ErrorResult("User (" + user.getEmail() + "#" + user.getRole() + ") have already connected");
        }

        connectedSessionInformation.put(session, SessionInformation
                .builder()
                .sessionId(session.getId())
                .user(user)
                .informationPayload(new AgentInformationPayload())
                .agentInitializationPayload(new AgentInitializationPayload())
                .build());


        return new SuccessResult("User (" + user.getEmail() + "#" + user.getRole() + ") connected to agent live-information");
    }

    @Override
    public Result removeManager(WebSocketSession session) {

        if (!isSessionExists(session)) {
            return new ErrorResult("Session (" + session.getAttributes().get("username") + ") not found");
        }

        connectedSessionInformation.remove(session);

        return new SuccessResult("MANAGER/ADMIN Disconnected: " + session.getAttributes().get("username"));
    }

    @Override
    public CustomMessage<ManagerAgentInformationPayload> getConnectedAgentLiveMessage() {

        List<AgentSessionInformationResponse> responses = convertSessionToSessionResponse(getSessionInformationByRole(List.of(Role.AGENT)));

        ManagerAgentInformationPayload payload = ManagerAgentInformationPayload.builder()
                .connectedAgentAmount(responses.size())
                .lastUpdateDate(LocalDateTime.now())
                .agentSessionInformationResponses(responses)
                .build();

        return messageUtil.createManagerAgentInformationMessage("List of connected agent information",payload);

    }

    @Override
    public Result broadcastAgentInfo() {
        List<WebSocketSession> managerSession = getSessionByRole(Arrays.asList(Role.MANAGER, Role.ADMIN));

        CustomMessage<ManagerAgentInformationPayload> liveMessage = getConnectedAgentLiveMessage();
        String message;
        try {
            message = messageUtil.customMessageToJson(liveMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new ErrorResult("UEO: " + e.getMessage());
        }

        for (WebSocketSession session: managerSession) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                e.printStackTrace();
                return new ErrorResult("UEO: " + e.getMessage());
            }
        }

        return new SuccessResult("Broadcast Successfully");
    }

    public HashMap<WebSocketSession, SessionInformation> getConnectedSessionInformation() {
        return connectedSessionInformation;
    }

    private boolean isSessionExists(WebSocketSession session) {
        return connectedSessionInformation.keySet().contains(session);
    }

    private boolean isSessionEmailExists(String email, List<Role> includedRoles) {
        return connectedSessionInformation.values().stream()
                .filter(info -> includedRoles.contains(info.getUser().getRole()))
                .map(session -> session.getUser().getEmail())
                .toList().contains(email);
    }

    private List<SessionInformation> getSessionInformationByRole(List<Role> roles) {
        return connectedSessionInformation
                .values().stream()
                .filter(info -> roles.contains(info.getUser().getRole()))
                .toList();
    }

    private List<WebSocketSession> getSessionByRole(List<Role> roles) {
        return connectedSessionInformation
                .entrySet().stream()
                .filter(entry -> roles.contains(entry.getValue().getUser().getRole()))
                .map(Map.Entry::getKey)
                .toList();
    }

    private List<AgentSessionInformationResponse> convertSessionToSessionResponse(List<SessionInformation> sessionInformationList) {

        return sessionInformationList.stream()
                .map(info -> {
                    AgentInformationPayload informationPayload = info.getInformationPayload();
                    AgentInitializationPayload initializationPayload = info.getAgentInitializationPayload();
                    User user = info.getUser();

                    return AgentSessionInformationResponse.builder()
                            .sessionId(info.getSessionId())
                            .address(info.getAddress())
                            .agentId(user != null ? user.getId() : null)
                            .agentEmail(user != null ? user.getEmail() : null)
                            .agentStatus(informationPayload != null ? informationPayload.getAgentStatus() : AgentStatus.CRASHED)
                            .usingToken(informationPayload != null ? informationPayload.getUsingToken() : null)
                            .executingAttackPayload(informationPayload != null ? informationPayload.getExecutingAttackPayload() : null)
                            .executionHistory(informationPayload != null ? informationPayload.getExecutionHistory() : null)
                            .connectedAt(initializationPayload != null ? initializationPayload.getConnectedAt() : null)
                            .build();
                })
                .toList();

    }

}
