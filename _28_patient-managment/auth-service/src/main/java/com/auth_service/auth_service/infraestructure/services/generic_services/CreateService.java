package com.auth_service.auth_service.infraestructure.services.generic_services;

public interface CreateService<RQ, RS>
{
    RS create(RQ request);
}

