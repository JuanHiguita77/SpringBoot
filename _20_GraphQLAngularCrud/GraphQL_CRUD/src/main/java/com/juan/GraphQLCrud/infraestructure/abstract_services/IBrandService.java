package com.juan.GraphQLCrud.infraestructure.abstract_services;

import com.juan.GraphQLCrud.api.dto.BrandDTO;

import com.juan.GraphQLCrud.infraestructure.services.generic_services.CreateService;
import com.juan.GraphQLCrud.infraestructure.services.generic_services.DeleteService;
import com.juan.GraphQLCrud.infraestructure.services.generic_services.GetAllService;
import com.juan.GraphQLCrud.infraestructure.services.generic_services.GetService;
import com.juan.GraphQLCrud.infraestructure.services.generic_services.UpdateService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBrandService extends GetAllService<BrandDTO>, GetService<BrandDTO, Long>, CreateService<BrandDTO, BrandDTO>, UpdateService<BrandDTO, BrandDTO, Long>, DeleteService<Long>
{    
    Flux<BrandDTO> getAllBrandsFlux();

    Mono<BrandDTO> getOneMono(Long id);
}