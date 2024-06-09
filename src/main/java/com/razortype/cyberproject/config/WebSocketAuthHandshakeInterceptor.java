package com.razortype.cyberproject.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class WebSocketAuthHandshakeInterceptor implements HandshakeInterceptor {

    private final JwtService jwtService;
    private Logger logger = LoggerFactory.getLogger(LiveAgentInfoSocketHandler.class);

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;

            String token = extractTokenFromParam(servletRequest.getServletRequest());
            if (token == null) {
                token = extractTokenFromHeader(servletRequest.getServletRequest());
            }

            if (token != null && jwtService.isTokenValid(token)) {
                String username = jwtService.extractUsername(token);
                attributes.put("username", username);
            } else {
                return false; // Reject handshake if token is invalid
            }
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception ex) {
        // No need for implementation in this case
    }

    private String extractTokenFromHeader(HttpServletRequest servletRequest) {
        String authHeader = servletRequest.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    private String extractTokenFromParam(HttpServletRequest servletRequest) {
        String token = servletRequest.getParameter("token");
        if (token != null && !token.isEmpty()) {
            return token;
        }
        return null;
    }
}
