package com.riwi.BeautyCenter.infrastructure.abstract_services;

import com.riwi.BeautyCenter.api.dto.request.ClientRequest;
import com.riwi.BeautyCenter.api.dto.response.ClientResponse;
import com.riwi.BeautyCenter.infrastructure.services.interfaces.CrudService;

public interface IClientService extends CrudService<ClientRequest, ClientResponse, Long>
{

}
