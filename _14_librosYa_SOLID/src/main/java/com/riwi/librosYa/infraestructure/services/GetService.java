package com.riwi.librosYa.infraestructure.services;

public interface GetService<RS, ID>
{
    RS get(ID id);
}

