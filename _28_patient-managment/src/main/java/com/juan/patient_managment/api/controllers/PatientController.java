package com.juan.patient_managment.api.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.juan.patient_managment.api.dto.PatientRequestDTO;
import com.juan.patient_managment.api.dto.PatientResponseDTO;
import com.juan.patient_managment.infraestructure.services.PatientService;
import com.juan.patient_managment.util.enums.SortType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> getPatient(@PathVariable UUID id) {
        return ResponseEntity.ok(patientService.get(id));
    }

    @GetMapping
    public ResponseEntity<Page<PatientResponseDTO>> getAllPatients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "NONE") SortType sortType) {
        return ResponseEntity.ok(patientService.getAll(page, size, sortType));
    }

    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(@Validated @RequestBody PatientRequestDTO patientRequestDTO) {
        return ResponseEntity.ok(patientService.create(patientRequestDTO));
    }

    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable UUID id) {
        patientService.delete(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> updatePatient(@Validated @RequestBody PatientRequestDTO patientRequestDTO, @PathVariable UUID id) {
        return ResponseEntity.ok(patientService.update(patientRequestDTO, id));
    }
}
