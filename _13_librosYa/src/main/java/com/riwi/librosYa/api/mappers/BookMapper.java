package com.riwi.librosYa.api.mappers;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

import com.riwi.librosYa.api.dto.DTOBook;
import com.riwi.librosYa.domain.entities.Book;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {ReservationMapper.class, LoanMapper.class})
public interface BookMapper 
{
    @Mappings({
        @Mapping(target = "reservations", source = "reservations"),
        @Mapping(target = "loans", source = "loans")
    })
    DTOBook toDTOBook(Book book);

    @InheritInverseConfiguration(name = "toDTOBook")
    @Mappings({
        @Mapping(target = "reservations", source = "reservations"),
        @Mapping(target = "loans", source = "loans")
    })
    Book toEntity(DTOBook dtoBook);

    List<DTOBook> toDTOBookList(List<Book> bookList);

    List<Book> toEntityList(List<DTOBook> dtoBookList);
}
