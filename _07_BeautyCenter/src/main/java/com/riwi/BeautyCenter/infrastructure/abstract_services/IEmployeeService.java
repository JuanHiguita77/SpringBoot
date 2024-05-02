package com.riwi.BeautyCenter.infrastructure.abstract_services;

import com.riwi.BeautyCenter.api.dto.request.EmployeeRequest;
import com.riwi.BeautyCenter.api.dto.response.EmployeeResponse;
import com.riwi.BeautyCenter.infrastructure.services.interfaces.CrudService;

public interface IEmployeeService extends CrudService<EmployeeRequest, EmployeeResponse, Long>
{

}
