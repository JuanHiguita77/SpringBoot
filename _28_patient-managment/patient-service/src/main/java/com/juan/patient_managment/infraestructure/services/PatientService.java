package com.juan.patient_managment.infraestructure.services;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.juan.patient_managment.api.dto.PatientRequestDTO;
import com.juan.patient_managment.api.dto.PatientResponseDTO;
import com.juan.patient_managment.api.mappers.PatientMapper;
import com.juan.patient_managment.domain.entities.Patient;
import com.juan.patient_managment.domain.repositories.PatientRepository;
import com.juan.patient_managment.grpc.BillingServiceGrpcClient;
import com.juan.patient_managment.infraestructure.abstract_services.IPatientService;
import com.juan.patient_managment.kafka.KafkaProducer;
import com.juan.patient_managment.util.enums.SortType;
import com.juan.patient_managment.util.exceptions.BadRequestException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientService implements IPatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final BillingServiceGrpcClient billingServiceGrpcClient;
    private final KafkaProducer kafkaProducer;
    
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PatientService.class);

    @Override
    public PatientResponseDTO get(UUID id) {
        return patientMapper.entityToDTO(patientRepository.findById(id).orElse(null));
        }

    @Override
    public Page<PatientResponseDTO> getAll(int page, int size, SortType sortType) {
        if (page < 0)
        page = 0;

        PageRequest pagination = null;

        switch (sortType) {
            case NONE -> pagination = PageRequest.of(page, size);
            case ASC -> pagination = PageRequest.of(page, size, Sort.by(Sort.Order.asc("registryDate")));
            case DESC -> pagination = PageRequest.of(page, size, Sort.by(Sort.Order.desc("registryDate")));
        }
        Pageable pageable = pagination;

        return patientRepository.findAll(pageable).map(patientMapper::entityToDTO);
    }

    @Override
    public PatientResponseDTO create(PatientRequestDTO patientRequestDTO) {
        // Validación para el email repetido
        patientRepository.findByEmail(patientRequestDTO.getEmail())
            .ifPresent(p -> {
                throw new BadRequestException("Email already registered!");
            });
    
        // Convertimos el DTO a entidad y lo guardamos
        Patient patientToSave = patientMapper.DTOtoEntity(patientRequestDTO);
        Patient savedPatient = patientRepository.save(patientToSave);
    
        // Creamos cuenta de facturación
        billingServiceGrpcClient.createBillingAccount(
            savedPatient.getId().toString(),
            savedPatient.getName(),
            savedPatient.getEmail()
        );
    
        log.info("Creating billing account for patient: {}", savedPatient.getId());
    
        // Enviamos evento al broker
        kafkaProducer.sendEvent(savedPatient);
    
        // Devolvemos el DTO con el ID ya generado
        return patientMapper.entityToDTO(savedPatient);
    }
    

    @Override
    public void delete(UUID id) {
        patientRepository.deleteById(id);
    }

    @Override
    public PatientResponseDTO update(PatientRequestDTO patientRequestDTO, UUID id) {

        patientRepository.findById(id)
        .orElseThrow(() -> new BadRequestException("Patient not found!"));

        Patient updatePatient = patientMapper.DTOtoEntity(patientRequestDTO);
        updatePatient.setId(id);
        
        return patientMapper.entityToDTO(patientRepository.save(updatePatient));
    }
}

