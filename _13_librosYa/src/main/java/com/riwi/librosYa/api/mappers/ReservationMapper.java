package com.riwi.librosYa.api.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

import com.riwi.librosYa.api.dto.DTOReservation;
import com.riwi.librosYa.domain.entities.Reservation;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReservationMapper {

    @Mappings({
        @Mapping(target = "userId", source = "user.id"),
        @Mapping(target = "bookId", source = "book.id")
    })
    DTOReservation toDTOReservation(Reservation reservation);

    @Mappings({
        @Mapping(target = "user.id", source = "userId"),
        @Mapping(target = "book.id", source = "bookId")
    })
    Reservation toEntity(DTOReservation dtoReservation);
}
