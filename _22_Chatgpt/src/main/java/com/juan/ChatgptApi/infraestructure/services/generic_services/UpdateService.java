package com.juan.ChatgptApi.infraestructure.services.generic_services;

public interface UpdateService<RQ, RS, ID>
{
    RS update(RQ request, ID id);
}

