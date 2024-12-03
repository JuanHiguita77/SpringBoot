package com.juan.inventory_service.infraestructure.services.generic_services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


//JUST FOR EXAMPLE, THIS INTERFACE IS UNNECESSARY
public interface GetAllService<RS> 
{
    Page<RS> getAll(Pageable pageable);    
}
