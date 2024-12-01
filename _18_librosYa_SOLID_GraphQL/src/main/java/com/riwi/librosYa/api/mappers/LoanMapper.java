package com.riwi.librosYa.api.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

import com.riwi.librosYa.api.dto.DTOLoan;
import com.riwi.librosYa.domain.entities.Loan;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LoanMapper {

    @Mappings({
        @Mapping(target = "userId", source = "user.id"),
        @Mapping(target = "bookId", source = "book.id")
    })
    DTOLoan toDTOLoan(Loan loan);

    @Mappings({
        @Mapping(target = "user.id", source = "userId"),
        @Mapping(target = "book.id", source = "bookId")
    })
    Loan toEntity(DTOLoan dtoLoan);
}
