package com.riwi.librosYa.infraestructure.abstract_Services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.riwi.librosYa.api.dto.DTOLoan;
import com.riwi.librosYa.infraestructure.services.CreateService;
import com.riwi.librosYa.infraestructure.services.DeleteService;
import com.riwi.librosYa.infraestructure.services.GetService;
import com.riwi.librosYa.infraestructure.services.UpdateService;

public interface ILoanService extends CreateService<DTOLoan, DTOLoan>, 
                                    UpdateService<DTOLoan, DTOLoan, Long>, 
                                    GetService<DTOLoan, Long>, 
                                    DeleteService<Long>
{
    public Page<DTOLoan> getLoansByUserId(Long id, Pageable pageable);
}
