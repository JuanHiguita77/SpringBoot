package com.juan.patient_managment.api.mappers;

import com.juan.patient_managment.api.dto.PatientRequestDTO;
import com.juan.patient_managment.api.dto.PatientResponseDTO;
import com.juan.patient_managment.domain.entities.Patient;

import java.util.List;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    
    Patient DTOtoEntity(PatientRequestDTO patientRequestdto);

    PatientResponseDTO entityToDTO(Patient patient);

    List<PatientResponseDTO> entitiesToDTOs(List<Patient> patient);
}
