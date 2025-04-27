package com.juan.patient_managment.api.dto;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Builder;

@Builder
public record PatientResponseDTO(
    UUID id,
    String name,
    String email,
    String phone,
    String address,
    LocalDate birthDate,
    LocalDate registryDate
){}



