package com.juan.ChatgptApi.api.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestDTO {
    private String model;
    private List<MessageDTO> messageDTO;

    //Constructor
    public RequestDTO(String model, String prompt){
        this.model = model;
        messageDTO = new ArrayList<>();

        messageDTO.add(new MessageDTO("user", prompt));
    }
}
