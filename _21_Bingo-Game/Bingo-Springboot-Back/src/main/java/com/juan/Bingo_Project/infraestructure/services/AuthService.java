package com.juan.Bingo_Project.infraestructure.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.juan.Bingo_Project.api.dto.AuthResponse;
import com.juan.Bingo_Project.api.dto.UserDTO;
import com.juan.Bingo_Project.infraestructure.abstract_services.IAuthService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService implements IAuthService{

    @Autowired
    private final UserService userService;

    @Autowired
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO register(UserDTO request) {
        return userService.create(request);
    }

    @Override
    public AuthResponse login(UserDTO request) {
        UserDTO user = userService.findByUsername(request.getUserName());

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        String token = jwtTokenProvider.generateToken(user.getUserName());
        
        return new AuthResponse(token);
    }
}
