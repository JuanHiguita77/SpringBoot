package com.juan.ChatgptApi.infraestructure.services.generic_services;

public interface GetService<RS, ID>
{
    RS get(ID id);
}

