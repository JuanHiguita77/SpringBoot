package com.auth_service.auth_service.infraestructure.abstract_services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.auth_service.auth_service.api.dto.request.LoginRequestDTO;


public interface IAuthService {
    Optional<String> login(LoginRequestDTO loginRequest);

    boolean validateToken(String token);
}
