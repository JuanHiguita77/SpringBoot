package com.juan.Bingo_Project.infraestructure.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.juan.Bingo_Project.api.dto.UserDTO;
import com.juan.Bingo_Project.domain.entities.UserEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserDTO UserEntityToUserDTO(UserEntity user);

    UserEntity UserDTOToUserEntity(UserDTO userDTO);
}
