package com.riwi.librosYa.infraestructure.services;

public interface CreateService<RQ, RS>
{
    RS create(RQ request);
}

