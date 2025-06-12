package com.auth_service.auth_service.infraestructure.services.generic_services;

public interface GetService<RS, ID>
{
    RS get(ID id);
}

