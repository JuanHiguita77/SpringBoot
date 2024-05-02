package com.riwi.BeautyCenter.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.riwi.BeautyCenter.domain.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Long>{
    
}
