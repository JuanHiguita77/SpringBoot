package com.riwi.BeautyCenter.api.dto.request;

import java.sql.Timestamp;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentRequest 
{
    @NotNull(message = "Date is required")
    @Future(message = "Date cite must be in the future")
    private Timestamp date_time;

    @NotNull(message = "Duration is required")
    @Max(value = 11, message = "Duration must be less than or equal to 11")
    private int duration;

    private String comments;

    @NotNull(message = "Client ID is required")
    private Long client_id;

    @NotNull(message = "Employee ID is required")
    private Long employee_id;  

    @NotNull(message = "Service ID is required")
    private Long service_id;   
}
