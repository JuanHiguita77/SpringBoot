package com.juan.Bingo_Project.infraestructure.services.generic_services;

import org.springframework.data.domain.Page;

//JUST FOR EXAMPLE, THIS INTERFACE IS UNNECESSARY
public interface GetAllService<RS> 
{
    Page<RS> getAll();    
}
