package com.juan.patient_managment.infraestructure.services.generic_services;

public interface CreateService<RQ, RS>
{
    RS create(RQ request);
}

