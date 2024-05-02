package com.riwi.BeautyCenter.infrastructure.services.interfaces;

import org.springframework.data.domain.Page;

//Creamos un generico para el crud pasandole los request, responses, y tipo dato llave primaria
public interface CrudService<RQ, RS, ID> 
{
    void delete(ID id);

    RS insert(RQ request);

    RS update(ID id, RQ request);

    Page<RS> list(int page, int size);

    RS findById(Long id);
}
