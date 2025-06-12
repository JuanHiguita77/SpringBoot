package com.auth_service.auth_service.infraestructure.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.auth_service.auth_service.domain.entities.User;
import com.auth_service.auth_service.domain.repositories.UserRepository;
import com.auth_service.auth_service.infraestructure.abstract_services.IUserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    
    private final UserRepository userRepository;

    @Override
    public Optional<User> findByEmail(String email) {
       return userRepository.findByEmail(email);
    }
}
