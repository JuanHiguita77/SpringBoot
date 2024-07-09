package com.riwi.librosYa.infraestructure.abstract_Services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.riwi.librosYa.api.dto.DTOReservation;
import com.riwi.librosYa.infraestructure.services.CreateService;
import com.riwi.librosYa.infraestructure.services.DeleteService;
import com.riwi.librosYa.infraestructure.services.GetService;
import com.riwi.librosYa.infraestructure.services.UpdateService;

public interface IReservationService extends CreateService<DTOReservation, DTOReservation>, 
                                    UpdateService<DTOReservation, DTOReservation, Long>, 
                                    GetService<DTOReservation, Long>, 
                                    DeleteService<Long>
{

   Page<DTOReservation> getReservationsByUserId(Long id, Pageable pageable);

   Page<DTOReservation> getReservationsByBookId(Long id, Pageable pageable);
}
