package com.juan.Bingo_Project.infraestructure.abstract_services;

import com.juan.Bingo_Project.api.dto.AuthResponse;
import com.juan.Bingo_Project.api.dto.UserDTO;

public interface IAuthService {
    AuthResponse login(UserDTO request);

    UserDTO register(UserDTO request);
}
