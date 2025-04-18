package com.riwi.librosYa.api.mappers;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

import com.riwi.librosYa.api.dto.DTOUser;
import com.riwi.librosYa.domain.entities.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {ReservationMapper.class, LoanMapper.class})
public interface UserMapper 
{
    @Mappings({
        @Mapping(target = "reservations", source = "reservations"),
        @Mapping(target = "loans", source = "loans"),
        @Mapping(target = "password", ignore = true)
    })
    DTOUser toDTOUser(User user);

    @InheritInverseConfiguration
    User toEntity(DTOUser dtoUser);
}
