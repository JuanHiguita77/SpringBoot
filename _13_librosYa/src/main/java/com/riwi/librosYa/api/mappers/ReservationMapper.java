package com.riwi.librosYa.api.mappers;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.riwi.librosYa.api.dto.DTOReservation;
import com.riwi.librosYa.domain.entities.Reservation;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReservationMapper 
{
    DTOReservation toDTOReservation(Reservation reservation);

    @InheritInverseConfiguration
    Reservation toEntity(DTOReservation dtoReservation);

    List<DTOReservation> toDTOReservationList(List<Reservation> reservationList);

    List<Reservation> toEntityList(List<DTOReservation> dtoReservationList);
}
