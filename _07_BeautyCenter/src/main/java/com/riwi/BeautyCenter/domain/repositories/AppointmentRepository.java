package com.riwi.BeautyCenter.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.riwi.BeautyCenter.domain.entities.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>{
    
}
