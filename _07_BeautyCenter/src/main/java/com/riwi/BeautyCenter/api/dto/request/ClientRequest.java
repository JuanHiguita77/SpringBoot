package com.riwi.BeautyCenter.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequest 
{
    private String first_name;
    private String last_name;
    private String phone;
    private String email; 
    //private Long appointment_id;
}
