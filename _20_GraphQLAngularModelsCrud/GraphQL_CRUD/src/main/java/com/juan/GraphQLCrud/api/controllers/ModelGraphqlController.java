package com.juan.GraphQLCrud.api.controllers;

import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import com.juan.GraphQLCrud.api.dto.ModelDTO;
import com.juan.GraphQLCrud.infraestructure.abstract_services.IModelService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ModelGraphqlController {
    
    private final IModelService modelService;

    //Querys
    @QueryMapping(name = "getAllModels")
    public Page<ModelDTO> getAll(@Argument int page, @Argument int size) {
        return modelService.getAll(page, size);
    }

    @QueryMapping(name = "getModelById")
    @PreAuthorize("isAuthenticated()")
    public ModelDTO get(@Argument Long id) {
        return modelService.get(id);
    }

    @QueryMapping(name = "getModelsByBrandId")
    @PreAuthorize("isAuthenticated()")
    public Page<ModelDTO> getModelsByBrandId(@Argument Long brandId, @Argument int page, @Argument int size) {
        return modelService.findModelsByBrandId(brandId, page, size);
    }

    //Mutations
    @MutationMapping(name = "createModel")
    @PreAuthorize("isAuthenticated()")
    public ModelDTO create(@Argument ModelDTO request) {
        return modelService.create(request);
    }

    @MutationMapping(name = "updateModel")
    @PreAuthorize("isAuthenticated()")
    public ModelDTO update(@Argument ModelDTO request, @Argument Long id) {
        return modelService.update(request, id);
    }

    @MutationMapping(name = "deleteModel")
    @PreAuthorize("isAuthenticated()")
    public Boolean delete(@Argument Long id) {
        modelService.delete(id);

        return true;
    }
}
