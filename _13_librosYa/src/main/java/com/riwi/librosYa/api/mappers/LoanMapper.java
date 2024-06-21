package com.riwi.librosYa.api.mappers;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.riwi.librosYa.api.dto.DTOLoan;
import com.riwi.librosYa.domain.entities.Loan;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LoanMapper 
{
    DTOLoan toDTOLoan(Loan loan);

    @InheritInverseConfiguration
    Loan toEntity(DTOLoan dtoLoan);

    List<DTOLoan> toDTOLoanList(List<Loan> loanList);

    List<Loan> toEntityList(List<DTOLoan> dtoLoanList);
}
