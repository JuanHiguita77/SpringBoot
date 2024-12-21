package com.juan.Bingo_Project.infraestructure.mappers;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import com.juan.Bingo_Project.api.dto.UserDTO;
import com.juan.Bingo_Project.domain.entities.UserEntity;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = false))
public interface UserMapper {

    UserDTO userToUserDTO(UserEntity user);

    UserEntity userDTOToUser(UserDTO userDTO);
}
