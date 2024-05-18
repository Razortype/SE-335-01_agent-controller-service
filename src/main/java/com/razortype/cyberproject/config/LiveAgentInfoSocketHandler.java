package com.razortype.cyberproject.config;

import com.razortype.cyberproject.core.results.DataResult;
import com.razortype.cyberproject.core.results.Result;
import com.razortype.cyberproject.core.utils.AgentUtil;
import com.razortype.cyberproject.entity.User;
import com.razortype.cyberproject.service.abstracts.AgentService;
import com.razortype.cyberproject.service.abstracts.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

@Component
public class LiveAgentInfoSocketHandler extends TextWebSocketHandler {

    private final UserService userService;
    private final AgentService agentService;
    private final AgentUtil agentUtil;

    private Logger logger = LoggerFactory.getLogger(LiveAgentInfoSocketHandler.class);

    @Autowired
    public LiveAgentInfoSocketHandler(UserService userService,
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

        Result managerAddResult = agentService.addManager(session, user);
        if (!managerAddResult.isSuccess()) {
            logger.warn("Socket: " + managerAddResult.getMessage());
            session.sendMessage(new TextMessage(managerAddResult.getMessage()));
            session.close();
            return;
        }

        logger.info("MANAGER/ADMIN connected to /live-agent : " + session.getRemoteAddress());
        session.sendMessage(new TextMessage("#connected ~> NEED TO SEND INITIAL CONNECTED AGENT INFO"));

    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("Received message on live-agent ("+ session.getRemoteAddress() +"): " + message.getPayload());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        Result managerRemoveResult = agentService.removeManager(session);
        if (!managerRemoveResult.isSuccess()) {
            logger.warn("Socket: " + managerRemoveResult.getMessage());
            return;
        }

        logger.info("MANAGER/ADMIN disconnected from /live-agent : " + session.getRemoteAddress());
    }

}
