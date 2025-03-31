package com.juan.chat.websocket.config;

import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.juan.chat.websocket.entity.ChatMessage;
import com.juan.chat.websocket.type.MessageType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j  
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messagingTemplate; 

    // method for handling the connection event
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        log.info("Received a new web socket connection");
    }

    //Method for handling the disconnection event
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = headerAccessor.getSessionAttributes().get("username").toString();

        if (username != null) {
            log.info("User Disconnected : " + username);

            // Create a message for the user who left the chat
            ChatMessage chatMessage = ChatMessage.builder()
                    .type(MessageType.LEAVE)
                    .sender(username)
                    .build();

            // Send the message to the public topic
            messagingTemplate.convertAndSend("/topic/public", chatMessage);        
        }
    }
}
