package com.riwi.librosYa.api.mappers;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.riwi.librosYa.api.dto.DTOBook;
import com.riwi.librosYa.domain.entities.Book;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookMapper 
{
    DTOBook toDTOBook(Book book);

    @InheritInverseConfiguration
    Book toEntity(DTOBook dtoBook);

    List<DTOBook> toDTOBookList(List<Book> bookList);

    List<Book> toEntityList(List<DTOBook> dtoBookList);
}
