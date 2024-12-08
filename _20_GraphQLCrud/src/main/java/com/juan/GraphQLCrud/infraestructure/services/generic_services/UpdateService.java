package com.juan.GraphQLCrud.infraestructure.services.generic_services;

public interface UpdateService<RQ, RS, ID>
{
    RS update(RQ request, ID id);
}

