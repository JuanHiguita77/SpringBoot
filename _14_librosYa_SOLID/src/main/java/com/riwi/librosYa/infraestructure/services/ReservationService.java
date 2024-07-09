package com.riwi.librosYa.infraestructure.services;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.riwi.librosYa.api.dto.DTOReservation;
import com.riwi.librosYa.api.mappers.ReservationMapper;
import com.riwi.librosYa.domain.entities.Reservation;
import com.riwi.librosYa.domain.repository.ReservationRepository;
import com.riwi.librosYa.infraestructure.abstract_Services.IReservationService;
import com.riwi.librosYa.util.exceptions.BadRequestException;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ReservationService implements IReservationService
{
    @Autowired
    private ReservationRepository reservationRepository;

    private ReservationMapper getMapper()
    {
        return Mappers.getMapper(ReservationMapper.class);
    }

    @Override
    public DTOReservation create(DTOReservation request) {
        Reservation reservationEntity = getMapper().toEntity(request);
        return getMapper().toDTOReservation(reservationRepository.save(reservationEntity));
    }

    @Override
    public DTOReservation get(Long id) {
        Reservation reservationEntity = find(id);
        return getMapper().toDTOReservation(reservationEntity);
    }

    @Override
    public DTOReservation update(DTOReservation request, Long id) {
        Reservation existingEntity = find(id);

        Reservation updatedEntity = getMapper().toEntity(request);
        updatedEntity.setId(existingEntity.getId());
        
        return getMapper().toDTOReservation(reservationRepository.save(updatedEntity));
    }

    @Override
    public void delete(Long id) {
        Reservation reservationEntity = find(id);
        reservationRepository.delete(reservationEntity);
    }

    @Override
    public Page<DTOReservation> getReservationsByUserId(Long userId, Pageable pageable) {
        Page<Reservation> reservations = reservationRepository.findByUserId(userId, pageable);

        return reservations.map(getMapper()::toDTOReservation);
    }

    @Override
    public Page<DTOReservation> getReservationsByBookId(Long bookId, Pageable pageable) {
        Page<Reservation> reservations = reservationRepository.findByBookId(bookId, pageable);
        
        return reservations.map(getMapper()::toDTOReservation);
    }

    private Reservation find(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("No reservation found with the supplied ID"));
    }
}