package com.juan.ChatgptApi.infraestructure.services;

import org.springframework.stereotype.Service;

@Service
public class ChatService {
    public String getPrompt(String message) {
        String prompt = "Responde hacerca sobre: " + message;

        return prompt;
    }
}
