package com.juan.GraphQLCrud.api.controllers;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import com.juan.GraphQLCrud.api.dto.user.RegisterRequest;
import com.juan.GraphQLCrud.api.dto.user.LoginRequest;
import com.juan.GraphQLCrud.api.dto.user.TokenResponse;
import com.juan.GraphQLCrud.infraestructure.services.authentication.AuthService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;

    @MutationMapping(name = "register")
    @PreAuthorize("permitAll()")
    public TokenResponse register(@Argument("request") RegisterRequest request) {
        return authService.register(request);
    }

    @MutationMapping(name = "login")
    @PreAuthorize("permitAll()")
    public TokenResponse login(@Argument("request") LoginRequest request) {
        return authService.login(request);
    }

    @MutationMapping(name = "refreshToken")
    @PreAuthorize("isAuthenticated()")
    public TokenResponse refreshToken(@Argument("refreshToken") String authHeader) {
        return authService.refreshToken(authHeader);
    }

    @MutationMapping(name = "logout")
    public void logout(@Argument("token") String authHeader) {
        authService.logout(authHeader);
    }
}
