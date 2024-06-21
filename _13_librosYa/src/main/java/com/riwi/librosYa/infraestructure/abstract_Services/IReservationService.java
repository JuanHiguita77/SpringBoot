package com.riwi.librosYa.infraestructure.abstract_Services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.riwi.librosYa.api.dto.DTOReservation;
import com.riwi.librosYa.infraestructure.services.CrudService;

public interface IReservationService extends CrudService<DTOReservation, DTOReservation, Long>
{

   Page<DTOReservation> getReservationsByUserId(Long id, Pageable pageable);

   Page<DTOReservation> getReservationsByBookId(Long id, Pageable pageable);
}
