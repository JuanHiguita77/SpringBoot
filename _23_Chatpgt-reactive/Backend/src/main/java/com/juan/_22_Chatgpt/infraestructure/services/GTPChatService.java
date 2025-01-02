package com.juan._22_Chatgpt.infraestructure.services;

import org.mvnsearch.chatgpt.model.ChatCompletion;

import com.juan._22_Chatgpt.api.dto.MessageDTO;

import reactor.core.publisher.Mono;

public interface GTPChatService {

    @ChatCompletion("Responde como un programador senior con 10 años de experiencia lo siguiente: (0)")//prompt position (0)
    Mono<MessageDTO> chat(MessageDTO messageDTO);    
}
