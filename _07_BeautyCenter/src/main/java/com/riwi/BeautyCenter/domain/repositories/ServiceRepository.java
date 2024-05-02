package com.riwi.BeautyCenter.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.riwi.BeautyCenter.domain.entities.Service;

public interface ServiceRepository extends JpaRepository<Service, Long>{
    
}
