package com.riwi.OnlineLearning.infraestructure.abstract_services;

import com.riwi.OnlineLearning.api.dto.request.CourseReq;
import com.riwi.OnlineLearning.api.dto.response.CourseResp;
import com.riwi.OnlineLearning.infraestructure.services.CrudService;

public interface ICourseService extends CrudService<CourseReq, CourseResp, Long> 
{
    public String FIELD_BY_SORT = "courseName";
}
