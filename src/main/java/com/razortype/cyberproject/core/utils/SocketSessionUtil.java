package com.razortype.cyberproject.core.utils;

import com.razortype.cyberproject.core.results.DataResult;
import com.razortype.cyberproject.entity.User;
import com.razortype.cyberproject.service.abstracts.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class SocketSessionUtil {
    private final UserService userService;

    public User checkAndGetSessionAuth(WebSocketSession session) throws IOException {
        String username = (String) session.getAttributes().get("username");
        if (username == null) {
            session.sendMessage(new TextMessage("Session requires authentication"));
            session.close();
            return null;
        }
        DataResult userResult = userService.getUserByEmail(username);
        if (!userResult.isSuccess()) {
            session.sendMessage(new TextMessage(userResult.getMessage()));
            session.close();
            return null;
        }
        return (User) userResult.getData();
    }

}
