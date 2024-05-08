package com.razortype.cyberproject.config;

import com.razortype.cyberproject.core.objects.AgentMessage;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

@Component
public class AgentMessageChannel implements MessageChannel {

    @Override
    public boolean send(Message<?> message) {
        // Your custom logic for sending AgentMessage
        if (message.getPayload() instanceof AgentMessage) {
            AgentMessage agentMessage = (AgentMessage) message.getPayload();
            System.out.println("Sending agent message: " + agentMessage.getMessageType() + ", " + agentMessage.getPayload());
            // Here you can handle the message accordingly, such as sending it to WebSocket clients
            return true; // Return true if the message was sent successfully
        } else {
            throw new MessagingException("Invalid message payload type");
        }
    }

    @Override
    public boolean send(Message<?> message, long timeout) {
        // Implement your logic for sending messages with a timeout
        return send(message); // For simplicity, just delegate to the send(Message<?>) method
    }
}
