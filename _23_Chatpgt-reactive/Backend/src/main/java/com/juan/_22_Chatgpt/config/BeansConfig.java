package com.juan._22_Chatgpt.config;

import org.mvnsearch.chatgpt.spring.client.ChatGPTServiceProxyFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.juan._22_Chatgpt.infraestructure.services.GTPChatService;

@Configuration
public class BeansConfig {
    
    @Bean
    public GTPChatService gtpChatService(ChatGPTServiceProxyFactory proxyFactory) {
        return proxyFactory.createClient(GTPChatService.class);
    }
}
