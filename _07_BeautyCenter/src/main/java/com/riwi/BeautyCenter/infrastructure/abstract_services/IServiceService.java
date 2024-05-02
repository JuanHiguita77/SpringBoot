package com.riwi.BeautyCenter.infrastructure.abstract_services;

import com.riwi.BeautyCenter.api.dto.request.ServiceRequest;
import com.riwi.BeautyCenter.api.dto.response.ServiceResponse;
import com.riwi.BeautyCenter.infrastructure.services.interfaces.CrudService;

public interface IServiceService extends CrudService<ServiceRequest, ServiceResponse, Long>
{
    
}
