package com.auth_service.auth_service.api.controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth_service.auth_service.api.dto.request.LoginRequestDTO;
import com.auth_service.auth_service.api.dto.response.LoginResponseDTO;
import com.auth_service.auth_service.infraestructure.abstract_services.IAuthService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@Api(tags = "Auth", description = "Authentication and Authorization")
@RequiredArgsConstructor
public class AuthController {
    
    private final IAuthService authService;

    @ApiOperation(value = "Login", notes = "Authenticate user and return access token")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully authenticated"),
        @ApiResponse(code = 401, message = "Unauthorized")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Validated @RequestBody LoginRequestDTO loginRequest) {
        // Logic to authenticate user and generate access token
        Optional<String> token = authService.login(loginRequest);
        
        if (token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Unauthorized
        }

        String accessToken = token.get();

        return ResponseEntity.ok(new LoginResponseDTO(accessToken));
    }

    @ApiOperation(value = "Validate Token", notes = "Validate access token")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Token is valid"),
        @ApiResponse(code = 401, message = "Unauthorized")
    })
    @GetMapping("/validate")
    public ResponseEntity<Void> validateToken(@RequestHeader("Authorization") String token) {
        // Log the received token
        System.out.println("Received token: " + token);

        return authService.validateToken(token) ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Unauthorized
    }
}
