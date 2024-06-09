package com.razortype.cyberproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final AgentConnectionSocketHandler agentConnectionSocketHandler;
    private final LiveAgentInfoSocketHandler liveAgentInfoSocketHandler;
    private final WebSocketAuthHandshakeInterceptor authInterceptor;

    @Autowired
    public WebSocketConfig(AgentConnectionSocketHandler agentConnectionSocketHandler,
                           LiveAgentInfoSocketHandler liveAgentInfoSocketHandler,
                           WebSocketAuthHandshakeInterceptor authInterceptor) {
        this.agentConnectionSocketHandler = agentConnectionSocketHandler;
        this.liveAgentInfoSocketHandler = liveAgentInfoSocketHandler;
        this.authInterceptor = authInterceptor;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

        registry.addHandler(agentConnectionSocketHandler, "/connect-agent")
                .addInterceptors(authInterceptor);
        registry.addHandler(liveAgentInfoSocketHandler, "/live-agent")
                .setAllowedOrigins("*")
                .addInterceptors(authInterceptor);

    }
}

