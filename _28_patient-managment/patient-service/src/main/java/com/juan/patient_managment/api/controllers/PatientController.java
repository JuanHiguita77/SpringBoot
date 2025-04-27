package com.juan.patient_managment.api.controllers;

import com.juan.patient_managment.api.dto.PatientRequestDTO;
import com.juan.patient_managment.api.dto.PatientResponseDTO;
import com.juan.patient_managment.infraestructure.services.PatientService;
import com.juan.patient_managment.util.enums.SortType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@Tag(name = "Patients", description = "Operations related to patient management")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Operation(
        summary = "Get a patient by ID",
        description = "Returns a single patient resource identified by UUID",
        responses = {
            @ApiResponse(responseCode = "200", description = "Patient found",
                content = @Content(schema = @Schema(implementation = PatientResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Patient not found")
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> getPatient(
        @Parameter(description = "UUID of the patient", required = true)
        @PathVariable UUID id
    ) {
        return ResponseEntity.ok(patientService.get(id));
    }

    @Operation(
        summary = "List all patients",
        description = "Returns a paginated list of patients",
        responses = {
            @ApiResponse(responseCode = "200", description = "List of patients returned",
                content = @Content(schema = @Schema(implementation = PatientResponseDTO.class)))
        }
    )
    @GetMapping
    public ResponseEntity<Page<PatientResponseDTO>> getAllPatients(
        @Parameter(description = "Page number (0-based)", in = ParameterIn.QUERY)
        @RequestParam(defaultValue = "0") int page,

        @Parameter(description = "Page size", in = ParameterIn.QUERY)
        @RequestParam(defaultValue = "10") int size,

        @Parameter(description = "Sort type", in = ParameterIn.QUERY,
                   schema = @Schema(implementation = SortType.class))
        @RequestParam(defaultValue = "NONE") SortType sortType
    ) {
        return ResponseEntity.ok(patientService.getAll(page, size, sortType));
    }

    @Operation(
        summary = "Create a new patient",
        description = "Creates a new patient and returns the created resource",
        responses = {
            @ApiResponse(responseCode = "200", description = "Patient created",
                content = @Content(schema = @Schema(implementation = PatientResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed")
        }
    )
    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Patient creation request",
            required = true,
            content = @Content(schema = @Schema(implementation = PatientRequestDTO.class))
        )
        @Validated @RequestBody PatientRequestDTO patientRequestDTO
    ) {
        return ResponseEntity.ok(patientService.create(patientRequestDTO));
    }

    @Operation(
        summary = "Delete a patient by ID",
        description = "Deletes the patient with the given UUID",
        responses = {
            @ApiResponse(responseCode = "204", description = "Patient deleted"),
            @ApiResponse(responseCode = "404", description = "Patient not found")
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(
        @Parameter(description = "UUID of the patient to delete", required = true)
        @PathVariable UUID id
    ) {
        patientService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Update a patient by ID",
        description = "Updates the patient information",
        responses = {
            @ApiResponse(responseCode = "200", description = "Patient updated",
                content = @Content(schema = @Schema(implementation = PatientResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Patient not found"),
            @ApiResponse(responseCode = "400", description = "Validation error")
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> updatePatient(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Patient update request",
            required = true,
            content = @Content(schema = @Schema(implementation = PatientRequestDTO.class))
        )
        @Validated @RequestBody PatientRequestDTO patientRequestDTO,

        @Parameter(description = "UUID of the patient to update", required = true)
        @PathVariable UUID id
    ) {
        return ResponseEntity.ok(patientService.update(patientRequestDTO, id));
    }
}
