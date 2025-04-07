package com.juan.patient_managment.infraestructure.abstract_services;

import java.util.UUID;

import com.juan.patient_managment.api.dto.PatientRequestDTO;
import com.juan.patient_managment.api.dto.PatientResponseDTO;
import com.juan.patient_managment.infraestructure.services.generic_services.CreateService;
import com.juan.patient_managment.infraestructure.services.generic_services.DeleteService;
import com.juan.patient_managment.infraestructure.services.generic_services.GetAllService;
import com.juan.patient_managment.infraestructure.services.generic_services.GetService;
import com.juan.patient_managment.infraestructure.services.generic_services.UpdateService;

public interface IPatientService extends CreateService<PatientRequestDTO, PatientResponseDTO>,
                                        GetService<PatientResponseDTO, UUID>,GetAllService<PatientResponseDTO>, UpdateService<PatientRequestDTO, PatientResponseDTO, UUID>, DeleteService<UUID> {

    
}
