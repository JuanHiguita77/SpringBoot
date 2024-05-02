package com.riwi.BeautyCenter.infrastructure.abstract_services;

import com.riwi.BeautyCenter.api.dto.request.AppointmentRequest;
import com.riwi.BeautyCenter.api.dto.response.AppointmentResponse;
import com.riwi.BeautyCenter.infrastructure.services.interfaces.CrudService;

public interface IAppointmentService extends CrudService<AppointmentRequest, AppointmentResponse, Long>
{

}
