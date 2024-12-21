package com.juan.Bingo_Project.infraestructure.services.generic_services;

public interface UpdateService<RQ, RS, ID>
{
    RS update(RQ request, ID id);
}

