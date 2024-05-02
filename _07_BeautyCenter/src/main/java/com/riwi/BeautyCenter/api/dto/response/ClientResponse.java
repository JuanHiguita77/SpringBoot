package com.riwi.BeautyCenter.api.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponse 
{
    private Long client_id;
    private String first_name;
    private String last_name;
    private String phone;
    private String email;
    private List<AppointmentResponse> appointments;
}


  
