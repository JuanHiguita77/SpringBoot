package com.juan.GraphQLCrud.infraestructure.abstract_services;

import org.springframework.data.domain.Page;

import com.juan.GraphQLCrud.api.dto.ModelDTO;
import com.juan.GraphQLCrud.infraestructure.services.generic_services.CreateService;
import com.juan.GraphQLCrud.infraestructure.services.generic_services.DeleteService;
import com.juan.GraphQLCrud.infraestructure.services.generic_services.GetAllService;
import com.juan.GraphQLCrud.infraestructure.services.generic_services.GetService;
import com.juan.GraphQLCrud.infraestructure.services.generic_services.UpdateService;

public interface IModelService extends GetAllService<ModelDTO>, GetService<ModelDTO, Long>, CreateService<ModelDTO, ModelDTO>, UpdateService<ModelDTO, ModelDTO, Long>, DeleteService<Long> {
    Page<ModelDTO> findModelsByBrandId(Long brandId, int page, int size);
}
