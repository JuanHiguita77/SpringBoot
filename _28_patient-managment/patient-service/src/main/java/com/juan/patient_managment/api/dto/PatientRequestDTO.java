package com.juan.patient_managment.api.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientRequestDTO {

    @NotBlank(message = "Name is mandatory") 
    @Size(min = 3, max = 50, message = "Name should be between 3 and 50 characters") 
    private String name;
    
    @Email(message = "Email should be valid") 
    @NotBlank(message = "Email is mandatory") 
    private String email;

    @NotBlank(message = "Phone is mandatory")
    @Size(min = 10, max = 15, message = "Phone should be between 10 and 15 characters")
    private String phone;

    @NotNull(message = "Address is mandatory")
    @Size(min = 5, max = 100, message = "Address should be between 5 and 100 characters")
    private String address;

    @NotNull(message = "Birth date is mandatory")
    private LocalDate birthDate;

    @NotNull(message = "Registry date is mandatory")
    private LocalDate registryDate;

}
