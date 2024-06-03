package com.riwi.beautySalon.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.beautySalon.api.dto.request.ClientRegisterReq;
import com.riwi.beautySalon.api.dto.request.EmployeeRegisterReq;
import com.riwi.beautySalon.api.dto.request.LoginReq;
import com.riwi.beautySalon.api.dto.request.RegisterReq;
import com.riwi.beautySalon.api.dto.response.AuthResp;
import com.riwi.beautySalon.infraestructure.abstract_service.IAuthService;

import lombok.AllArgsConstructor;

//Configuracion de springSecurity
@RestController
@RequestMapping//No hay path general para dividir entre users autentificados y normales
@AllArgsConstructor
public class AuthController 
{
    @Autowired
    private final IAuthService authService;

    //Path para autentificados
    @PostMapping("/auth/login")
    public ResponseEntity<AuthResp> login(@Validated @RequestBody LoginReq request)
    {
        return ResponseEntity.ok(this.authService.login(request));
    }
    
    @PostMapping("/auth/register")
    public ResponseEntity<AuthResp> register(@Validated @RequestBody RegisterReq request)
    {
        return ResponseEntity.ok(this.authService.register(request));
    }

    @PostMapping("/auth/register/client")
    public ResponseEntity<AuthResp> registerClient(@Validated @RequestBody ClientRegisterReq request) 
    {
        return ResponseEntity.ok(this.authService.registerClient(request));
    }

    @PostMapping("/register/employee")
    public ResponseEntity<AuthResp> registerEmployee(@Validated @RequestBody EmployeeRegisterReq request) 
    {
        return ResponseEntity.ok(this.authService.registerEmployee(request));
    }
    
    
}