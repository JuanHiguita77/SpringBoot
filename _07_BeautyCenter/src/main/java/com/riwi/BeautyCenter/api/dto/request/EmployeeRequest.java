package com.riwi.BeautyCenter.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest 
{
    private String first_name;
    private String last_name;
    private String email;  
    private String phone;
    private String role;
    //private Long appointment_id;
}
