package com.riwi.BeautyCenter.api.dto.request;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceRequest 
{
    private String name;
    private String description;
    private BigDecimal price;
    //private Long appointment_id;  
}
