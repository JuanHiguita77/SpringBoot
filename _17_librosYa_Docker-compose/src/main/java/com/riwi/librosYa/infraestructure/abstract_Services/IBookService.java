package com.riwi.librosYa.infraestructure.abstract_Services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.riwi.librosYa.api.dto.DTOBook;
import com.riwi.librosYa.infraestructure.services.CreateService;
import com.riwi.librosYa.infraestructure.services.DeleteService;
import com.riwi.librosYa.infraestructure.services.GetService;
import com.riwi.librosYa.infraestructure.services.UpdateService;

public interface IBookService extends CreateService<DTOBook, DTOBook>, 
                                    UpdateService<DTOBook, DTOBook, Long>, 
                                    GetService<DTOBook, Long>, 
                                    DeleteService<Long>
{    
    Page<DTOBook> searchBook(String title, String genre, String author, Pageable pageable);
}
