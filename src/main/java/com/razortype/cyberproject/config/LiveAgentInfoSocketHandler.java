package com.razortype.cyberproject.config;

import com.razortype.cyberproject.core.results.Result;
import com.razortype.cyberproject.core.utils.SocketSessionUtil;
import com.razortype.cyberproject.entity.User;
import com.razortype.cyberproject.service.abstracts.SocketSessionService;
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
public class LiveAgentInfoSocketHandler extends TextWebSocketHandler {

    private final UserService userService;
    private final SocketSessionService socketSessionService;
    private final SocketSessionUtil socketSessionUtil;

    private Logger logger = LoggerFactory.getLogger(LiveAgentInfoSocketHandler.class);

    @Autowired
    public LiveAgentInfoSocketHandler(UserService userService,
                                      SocketSessionService socketSessionService,
                                      SocketSessionUtil socketSessionUtil) {
        this.userService = userService;
        this.socketSessionService = socketSessionService;
        this.socketSessionUtil = socketSessionUtil;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        User user = socketSessionUtil.checkAndGetSessionAuth(session);
        if (user == null) {
            logger.error("Socket Authentication not found: " + session.getRemoteAddress());
            return;
        }

        Result managerAddResult = socketSessionService.addManager(session, user);
        if (!managerAddResult.isSuccess()) {
            logger.warn("Socket: " + managerAddResult.getMessage());
            session.sendMessage(new TextMessage(managerAddResult.getMessage()));
            session.close();
            return;
        }

        logger.info("MANAGER/ADMIN connected to /live-agent : " + session.getRemoteAddress());

        String message = socketSessionService.getLastSendBroadcastInformation();
        if (message != null) {
            session.sendMessage(new TextMessage(message));
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("Received message on live-agent ("+ session.getRemoteAddress() +"): " + message.getPayload());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        Result managerRemoveResult = socketSessionService.removeManager(session);
        if (!managerRemoveResult.isSuccess()) {
            logger.warn("Socket: " + managerRemoveResult.getMessage());
            return;
        }

        logger.info("MANAGER/ADMIN disconnected from /live-agent : " + session.getRemoteAddress());
    }

}
