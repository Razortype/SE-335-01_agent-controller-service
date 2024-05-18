package com.razortype.cyberproject.config;

import com.razortype.cyberproject.core.results.Result;
import com.razortype.cyberproject.core.utils.AgentUtil;
import com.razortype.cyberproject.entity.User;
import com.razortype.cyberproject.service.abstracts.AgentService;
import com.razortype.cyberproject.service.abstracts.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class AgentConnectionSocketHandler extends TextWebSocketHandler {

    private final UserService userService;
    private final AgentService agentService;
    private final AgentUtil agentUtil;

    private Logger logger = LoggerFactory.getLogger(AgentConnectionSocketHandler.class);

    @Autowired
    public AgentConnectionSocketHandler(UserService userService,
                                        AgentService agentService,
                                        AgentUtil agentUtil) {
        this.userService = userService;
        this.agentService = agentService;
        this.agentUtil = agentUtil;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        User user = agentUtil.checkAndGetSessionAuth(session);
        if (user == null) {
            logger.error("Socket Authentication not found: " + session.getRemoteAddress());
            return;
        }

        Result agentAddResult = agentService.addAgentSession(session, user);
        if (!agentAddResult.isSuccess()) {
            logger.warn("Socket: " + agentAddResult.getMessage());
            session.sendMessage(new TextMessage(agentAddResult.getMessage()));
            session.close();
            return;
        }

        logger.info("Agent connected to /connect-agent : " + session.getRemoteAddress());
        session.sendMessage(new TextMessage("#connected ~> NEED TO SEND INITIALISING CONFIG COMPONENT"));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("Received message on connect-agent ("+ session.getRemoteAddress() +"): " + message.getPayload());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        Result agentRemoveResult = agentService.removeAgentSession(session);
        if (!agentRemoveResult.isSuccess()) {
            logger.warn("Socket: " + agentRemoveResult.getMessage());
            return;
        }

        logger.info("Agent disconnected from /connect-agent : " + session.getRemoteAddress());
    }

}
