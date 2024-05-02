package com.riwi.BeautyCenter.api.dto.request;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentRequest 
{
    private Timestamp date_time;
    private int duration;
    private String comments;
    private Long client_id;
    private Long employee_id;   
    private Long service_id;   
}
