package com.juan.patient_managment.domain.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.juan.patient_managment.domain.entities.Patient;

@Repository 
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    Optional<Patient> findByEmail(String email); //busca por email, devuelve un optional de patient
}
