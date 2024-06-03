package com.riwi.OnlineLearning.infraestructure.abstract_services;

import java.util.List;

import com.riwi.OnlineLearning.api.dto.request.LessonReq;
import com.riwi.OnlineLearning.api.dto.response.LessonResp;
import com.riwi.OnlineLearning.infraestructure.services.CrudService;

public interface ILessonService extends CrudService<LessonReq, LessonResp, Long> 
{
    public String FIELD_BY_SORT = "lessonTitle";
    List<LessonResp> getLessonsByCourseId(Long courseId);
}
