package com.juan.patient_managment.api.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record PatientRequestDTO(

    @NotBlank(message = "Name is mandatory") 
    @Size(min = 3, max = 50, message = "Name should be between 3 and 50 characters") 
    String name,
    
    @Email(message = "Email should be valid") 
    @NotBlank(message = "Email is mandatory") 
    String email,

    @NotBlank(message = "Phone is mandatory")
    @Size(min = 10, max = 15, message = "Phone should be between 10 and 15 characters")
     String phone,

    @NotNull(message = "Address is mandatory")
    @Size(min = 5, max = 100, message = "Address should be between 5 and 100 characters")
    String address,

    @NotNull(message = "Birth date is mandatory")
    LocalDate birthDate,

    @NotNull(message = "Registry date is mandatory")
    LocalDate registryDate

) {}
