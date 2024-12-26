package com.juan.Bingo_Project.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient.ResponseSpec;

import com.juan.Bingo_Project.api.dto.AuthResponse;
import com.juan.Bingo_Project.api.dto.UserDTO;
import com.juan.Bingo_Project.infraestructure.abstract_services.IAuthService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    @Autowired
    private final IAuthService authService;

    @PostMapping("/auth/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponse> login(@RequestBody UserDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
