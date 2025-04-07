package com.juan.patient_managment.infraestructure.services.generic_services;

public interface GetService<RS, ID>
{
    RS get(ID id);
}

