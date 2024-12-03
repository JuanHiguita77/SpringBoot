package com.juan.inventory_service.infraestructure.services.generic_services;

public interface GetService<RS, ID>
{
    RS getById(ID id);
}

