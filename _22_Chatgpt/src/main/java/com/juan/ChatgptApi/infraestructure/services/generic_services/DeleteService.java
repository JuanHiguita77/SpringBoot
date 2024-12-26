package com.juan.ChatgptApi.infraestructure.services.generic_services;

public interface DeleteService<ID>
{
    void delete(ID id);
}

