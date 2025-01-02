package com.juan._22_Chatgpt.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.juan._22_Chatgpt.api.dto.MessageDTO;
import com.juan._22_Chatgpt.infraestructure.services.GTPChatService;

import reactor.core.publisher.Mono;

@RestController
@CrossOrigin
public class ChatController {

    @Autowired
    private GTPChatService chatGPTService;

    @PostMapping("/chat")
    public Mono<MessageDTO> chat(@RequestBody MessageDTO content) {
        return chatGPTService.chat(content);
    }

}
