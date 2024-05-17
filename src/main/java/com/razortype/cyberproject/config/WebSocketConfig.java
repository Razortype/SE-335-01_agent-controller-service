package com.razortype.cyberproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final WebSocketAuthHandshakeInterceptor authInterceptor;

    public WebSocketConfig(WebSocketAuthHandshakeInterceptor authInterceptor) {
        this.authInterceptor = authInterceptor;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

        registry.addHandler(new AgentConnectionSocketHandler(), "/connect-agent")
                .addInterceptors(authInterceptor);
        registry.addHandler(new LiveAgentInfoSocketHandler(), "/live-agent")
                .addInterceptors(authInterceptor);

    }
}

