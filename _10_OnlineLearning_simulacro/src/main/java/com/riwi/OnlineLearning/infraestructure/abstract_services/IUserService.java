package com.riwi.OnlineLearning.infraestructure.abstract_services;

import com.riwi.OnlineLearning.api.dto.request.UserReq;
import com.riwi.OnlineLearning.api.dto.response.UserResp;
import com.riwi.OnlineLearning.infraestructure.services.CrudService;

public interface IUserService extends CrudService<UserReq, UserResp, Long> {
    public String FIELD_BY_SORT = "userName";
}
