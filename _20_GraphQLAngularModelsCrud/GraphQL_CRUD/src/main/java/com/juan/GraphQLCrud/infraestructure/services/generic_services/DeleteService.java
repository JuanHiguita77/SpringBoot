package com.juan.GraphQLCrud.infraestructure.services.generic_services;

public interface DeleteService<ID>
{
    void delete(ID id);
}

