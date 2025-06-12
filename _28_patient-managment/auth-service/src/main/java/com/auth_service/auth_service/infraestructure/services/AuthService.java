package com.auth_service.auth_service.infraestructure.services;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth_service.auth_service.api.dto.request.LoginRequestDTO;
import com.auth_service.auth_service.domain.entities.User;
import com.auth_service.auth_service.infraestructure.abstract_services.IAuthService;
import com.auth_service.auth_service.infraestructure.abstract_services.IUserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    
    private final IUserService userService; 
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;  
    
    /**
     * Authenticates a user and generates an access token.
     *
     * This method attempts to authenticate a user based on the provided login request.
     * It retrieves the user by email, verifies the password using the password encoder,
     * and if successful, generates a JWT token containing the user's email and role.
     *
     * @param loginRequest The login request containing the user's email and password.
     * @return An {@code Optional<String>} containing the generated token if authentication is successful,
     *         or an empty {@code Optional} if authentication fails.
     *
     * Logic:
     * - Retrieve the user by email using the `userService`.
     * - Verify the provided password matches the stored password using the `passwordEncoder`.
     * - If authentication is successful, generate a JWT token using the `jwtService`.
     */
    @Override
    public Optional<String> login(LoginRequestDTO loginRequest) {
        // Logic to authenticate user and generate access token
        Optional<String> token = userService.findByEmail(loginRequest.getEmail())
        .filter(e -> passwordEncoder.matches(loginRequest.getPassword(), e.getPassword()))
        .map(e -> jwtService.generateToken(e.getEmail(), e.getRole()));

        return token;
    }

    /**
     * Validates a given JWT token.
     *
     * This method checks if the token is not null, not empty, and starts with the "Bearer " prefix.
     * If the token passes these initial checks, it attempts to validate the token using the `jwtService`.
     * 
     * @param token The JWT token to be validated. It is expected to start with the "Bearer " prefix.
     * @return {@code true} if the token is valid; {@code false} otherwise.
     * 
     * Logs:
     * - Logs a message if the token is null, empty, or does not start with "Bearer ".
     * - Logs the token being validated (excluding the "Bearer " prefix).
     * - Logs a success message if the token is valid.
     * - Logs an error message if token validation fails, including the exception message.
     */
    @Override
    public boolean validateToken(String token) {
        
        if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            System.out.println("Invalid token: Token is null, empty, or does not start with 'Bearer '");
            return false; // Invalid token    
        }
        try {
            System.out.println("Validating token: " + token.substring(7));
            jwtService.validateToken(token.substring(7));
            System.out.println("Token is valid");
            return true; // Token is valid
        } catch (Exception e) {
            System.out.println("Token validation failed: " + e.getMessage());
            return false; // Token validation failed
        }
    }

}
