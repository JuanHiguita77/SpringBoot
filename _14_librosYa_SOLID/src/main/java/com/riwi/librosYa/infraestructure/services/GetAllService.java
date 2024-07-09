package com.riwi.librosYa.infraestructure.services;

import org.springframework.data.domain.Page;

import com.riwi.librosYa.util.enums.SortType;

//JUST FOR EXAMPLE, THIS INTERFACE IS UNNECESSARY
public interface GetAllService<RS> 
{
    Page<RS> getAll(int page, int size, SortType sort);    
}
