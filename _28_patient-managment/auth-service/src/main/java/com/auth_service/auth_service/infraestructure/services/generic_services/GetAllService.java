package com.auth_service.auth_service.infraestructure.services.generic_services;

import org.springframework.data.domain.Page;

//JUST FOR EXAMPLE, THIS INTERFACE IS UNNECESSARY
public interface GetAllService<RS> 
{
    Page<RS> getAll(int page, int size);    
}
