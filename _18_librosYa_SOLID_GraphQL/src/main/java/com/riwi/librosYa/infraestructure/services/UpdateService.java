package com.riwi.librosYa.infraestructure.services;

public interface UpdateService<RQ, RS, ID>
{
    RS update(RQ request, ID id);
}

