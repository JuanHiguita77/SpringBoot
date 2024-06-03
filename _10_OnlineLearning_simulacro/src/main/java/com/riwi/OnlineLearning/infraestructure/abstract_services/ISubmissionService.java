package com.riwi.OnlineLearning.infraestructure.abstract_services;

import java.util.List;

import com.riwi.OnlineLearning.api.dto.request.SubmissionReq;
import com.riwi.OnlineLearning.api.dto.response.SubmissionResp;
import com.riwi.OnlineLearning.infraestructure.services.CrudService;

public interface ISubmissionService extends CrudService<SubmissionReq, SubmissionResp, Long> 
{
    public String FIELD_BY_SORT = "submissionDate";

    List<SubmissionResp> getSubmissionsByAssignmentId(Long id);
    List<SubmissionResp> getSubmissionsByUserId(Long id);
}
