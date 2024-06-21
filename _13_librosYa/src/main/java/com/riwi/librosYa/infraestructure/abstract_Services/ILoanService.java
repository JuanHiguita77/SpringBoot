package com.riwi.librosYa.infraestructure.abstract_Services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.riwi.librosYa.api.dto.DTOLoan;
import com.riwi.librosYa.infraestructure.services.CrudService;

public interface ILoanService extends CrudService<DTOLoan, DTOLoan, Long>
{
    public Page<DTOLoan> getLoansByUserId(Long id, Pageable pageable);
}
