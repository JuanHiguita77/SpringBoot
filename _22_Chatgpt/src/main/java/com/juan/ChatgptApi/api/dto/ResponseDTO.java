package com.juan.ChatgptApi.api.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {
    
    private List<Choice> choices;

    @Data
    public static class Choice {
        private MessageDTO message;
        private int index;
    }
}
