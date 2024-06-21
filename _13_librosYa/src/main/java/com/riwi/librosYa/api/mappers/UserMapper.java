package com.riwi.librosYa.api.mappers;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.riwi.librosYa.api.dto.DTOUser;
import com.riwi.librosYa.domain.entities.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper 
{
    DTOUser toDTOUser(User user);

    @InheritInverseConfiguration
    User toEntity(DTOUser dtoUser);

    List<DTOUser> toDTOUserList(List<User> userList);

    List<User> toEntityList(List<DTOUser> dtoUserList);
}
