package com.juan.patient_managment.api.dto;

import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientResponseDTO {
    private UUID id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private LocalDate birthDate;
    private LocalDate registryDate;
}



