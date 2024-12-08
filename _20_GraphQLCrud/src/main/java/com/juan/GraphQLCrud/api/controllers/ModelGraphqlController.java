package com.juan.GraphQLCrud.api.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.Arguments;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.juan.GraphQLCrud.api.dto.ModelDTO;
import com.juan.GraphQLCrud.infraestructure.abstract_services.IModelService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class ModelGraphqlController {
    
    private final IModelService modelService;

    //Querys
    @QueryMapping(name = "getAllModels")
    public Page<ModelDTO> getAll(@Argument int page, @Argument int size) {
        return modelService.getAll(page, size);
    }

    @QueryMapping(name = "getModelById")
    public ModelDTO get(@Argument Long id) {
        return modelService.get(id);
    }

    //Mutations
    @MutationMapping(name = "createModel")
    public ModelDTO create(@Argument ModelDTO request) {
        return modelService.create(request);
    }

    @MutationMapping(name = "updateModel")
    public ModelDTO update(@Argument ModelDTO request, @Argument Long id) {
        return modelService.update(request, id);
    }

    @MutationMapping(name = "deleteModel")
    public void delete(@Argument Long id) {
        modelService.delete(id);
    }
}
