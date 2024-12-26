package com.juan.Bingo_Project.infraestructure.services;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.juan.Bingo_Project.api.dto.UserDTO;
import com.juan.Bingo_Project.domain.repositories.UserRepository;
import com.juan.Bingo_Project.infraestructure.abstract_services.IUserService;
import com.juan.Bingo_Project.infraestructure.mappers.UserMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDTO findByUsername(String username) {
        return userMapper.UserEntityToUserDTO(userRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("User not found")));
    }

    @Override
    public UserDTO create(UserDTO request) {
        return userMapper.UserEntityToUserDTO(userRepository.save(userMapper.UserDTOToUserEntity(request)));
    }
    
}
