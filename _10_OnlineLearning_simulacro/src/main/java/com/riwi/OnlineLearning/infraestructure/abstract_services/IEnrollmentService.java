package com.riwi.OnlineLearning.infraestructure.abstract_services;

import java.util.List;

import com.riwi.OnlineLearning.api.dto.request.EnrollmentReq;
import com.riwi.OnlineLearning.api.dto.response.EnrollmentResp;
import com.riwi.OnlineLearning.infraestructure.services.CrudService;

public interface IEnrollmentService extends CrudService<EnrollmentReq, EnrollmentResp, Long> 
{
    public String FIELD_BY_SORT = "enrollmentName";

    List<EnrollmentResp> getCoursesByUserId(Long id);
    List<EnrollmentResp> getUsersByCourseId(Long id);

}
