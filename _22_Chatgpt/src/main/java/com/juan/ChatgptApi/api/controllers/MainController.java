package com.juan.ChatgptApi.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.juan.ChatgptApi.api.dto.MessageDTO;
import com.juan.ChatgptApi.api.dto.RequestDTO;
import com.juan.ChatgptApi.api.dto.ResponseDTO;
import com.juan.ChatgptApi.infraestructure.services.ChatService;

@RestController
public class MainController {
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    @Autowired
    private ChatService chatService;

    @PostMapping("/chat")
    public ResponseEntity<MessageDTO> chat(@RequestBody String message){
        RequestDTO requestDTO = new RequestDTO(model, chatService.getPrompt(message));

        ResponseDTO responseDTO = restTemplate.postForObject(apiUrl, requestDTO, ResponseDTO.class);

        if(responseDTO.getChoices() == null || requestDTO == null || responseDTO.getChoices().isEmpty()){
            return ResponseEntity.badRequest().body(new MessageDTO("user", "Failed to get response from GPT-3"));
        }

        return ResponseEntity.ok(responseDTO.getChoices().get(0).getMessage());
    }
}
