package com.riwi.librosYa.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.riwi.librosYa.domain.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
    
}
