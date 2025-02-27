package com.juan.GraphQLCrud.infraestructure.services.generic_services;

public interface GetService<RS, ID>
{
    RS get(ID id);
}

