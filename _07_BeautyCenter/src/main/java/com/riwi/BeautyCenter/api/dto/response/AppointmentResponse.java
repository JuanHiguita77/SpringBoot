package com.riwi.BeautyCenter.api.dto.response;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponse 
{
    private Long appointment_id;
    private Timestamp date_time;
    private int duration;
    private String comments;
    private ClientResponse clientResponse;
    private EmployeeResponse employeeResponse;
    private ServiceResponse serviceResponse;
}


  
