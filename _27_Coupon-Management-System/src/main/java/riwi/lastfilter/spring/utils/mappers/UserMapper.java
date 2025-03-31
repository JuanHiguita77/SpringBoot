package riwi.lastfilter.spring.utils.mappers;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import riwi.lastfilter.spring.api.dto.request.UserRequest;
import riwi.lastfilter.spring.api.dto.response.UserResponse;
import riwi.lastfilter.spring.domain.entities.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserResponse toDTOEntity(User user);

    @InheritInverseConfiguration(name = "toDTOEntity")
    User toEntity(UserRequest userReq);
}



