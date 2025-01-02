package com.juan._22_Chatgpt.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageDTO {
    private String role;
    private String content;
}
