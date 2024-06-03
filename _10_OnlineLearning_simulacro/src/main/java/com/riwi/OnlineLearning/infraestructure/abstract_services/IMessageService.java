package com.riwi.OnlineLearning.infraestructure.abstract_services;

import java.util.List;

import com.riwi.OnlineLearning.api.dto.request.MessageReq;
import com.riwi.OnlineLearning.api.dto.response.MessageResp;
import com.riwi.OnlineLearning.infraestructure.services.CrudService;

public interface IMessageService extends CrudService<MessageReq, MessageResp, Long> 
{
    public String FIELD_BY_SORT = "sendDate";

    List<MessageResp> getMessagesBetweenUsers(Long senderId, Long receiverId);
    List<MessageResp> getMessagesByCourseId(Long courseId);
}