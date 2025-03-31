package com.juan.GraphQLCrud.infraestructure.services.generic_services;

public interface CreateService<RQ, RS>
{
    RS create(RQ request);
}

