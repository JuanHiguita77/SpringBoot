package com.riwi.BeautyCenter.api.dto.response;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceResponse 
{
    private Long service_id;
    private String name;
    private String description;
    private BigDecimal price;
    private List<AppointmentResponse> appointments;
}


  
