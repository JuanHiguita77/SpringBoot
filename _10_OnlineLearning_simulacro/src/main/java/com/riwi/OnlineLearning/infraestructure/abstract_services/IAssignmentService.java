package com.riwi.OnlineLearning.infraestructure.abstract_services;

import java.util.List;

import com.riwi.OnlineLearning.api.dto.request.AssignmentReq;
import com.riwi.OnlineLearning.api.dto.response.AssignmentResp;
import com.riwi.OnlineLearning.infraestructure.services.CrudService;

public interface IAssignmentService extends CrudService<AssignmentReq, AssignmentResp, Long> 
{
    public String FIELD_BY_SORT = "dueDate";
    
    List<AssignmentResp> getAssignmentsByLessonId(Long id);
}
