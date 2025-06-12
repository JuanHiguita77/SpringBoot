package com.auth_service.auth_service.infraestructure.abstract_services;

import java.util.Optional;

import com.auth_service.auth_service.domain.entities.User;

public interface IUserService {
    Optional<User> findByEmail(String email); // Method to find a user by email
}
