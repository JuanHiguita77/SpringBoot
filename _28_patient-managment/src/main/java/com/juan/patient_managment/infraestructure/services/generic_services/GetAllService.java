package com.juan.patient_managment.infraestructure.services.generic_services;

import org.springframework.data.domain.Page;

import com.juan.patient_managment.util.enums.SortType;

//JUST FOR EXAMPLE, THIS INTERFACE IS UNNECESSARY
public interface GetAllService<RS> 
{
    Page<RS> getAll(int page, int size, SortType sort);    
}
