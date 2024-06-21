package com.riwi.librosYa.infraestructure.abstract_Services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.riwi.librosYa.api.dto.DTOBook;
import com.riwi.librosYa.infraestructure.services.CrudService;

public interface IBookService extends CrudService<DTOBook, DTOBook, Long>
{    
    Page<DTOBook> searchBook(String title, String genre, String author, Pageable pageable);
}
