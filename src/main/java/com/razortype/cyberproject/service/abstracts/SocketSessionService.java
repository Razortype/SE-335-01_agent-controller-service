package com.razortype.cyberproject.service.abstracts;

import com.razortype.cyberproject.core.messages.CustomMessage;
import com.razortype.cyberproject.core.messages.payloads.ManagerAgentInformationPayload;
import com.razortype.cyberproject.core.objects.SessionInformation;
import com.razortype.cyberproject.core.results.Result;
import com.razortype.cyberproject.entity.User;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;

public interface SocketSessionService {

    Result addAgentSession(WebSocketSession session, User user);
    Result removeAgentSession(WebSocketSession session);
    Result handleReceivedMessage(WebSocketSession session, TextMessage message);

    Result addManager(WebSocketSession session, User user);
    Result removeManager(WebSocketSession session);
    CustomMessage<ManagerAgentInformationPayload> getConnectedAgentLiveMessage();
    Result broadcastAgentInfo();
    HashMap<WebSocketSession, SessionInformation> getConnectedSessionInformation();

}
