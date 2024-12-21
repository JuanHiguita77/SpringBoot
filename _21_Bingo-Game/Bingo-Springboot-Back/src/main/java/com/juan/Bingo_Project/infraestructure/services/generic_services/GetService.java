package com.juan.Bingo_Project.infraestructure.services.generic_services;

public interface GetService<RS, ID>
{
    RS get(ID id);
}

