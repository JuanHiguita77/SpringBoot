package com.juan.GraphQLCrud.api.controllers;

import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;

import com.juan.GraphQLCrud.api.dto.BrandDTO;
import com.juan.GraphQLCrud.infraestructure.abstract_services.IBrandService;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@AllArgsConstructor
public class BrandGraphqlController {
    
    private final IBrandService brandService;

    //Querys
    @QueryMapping(name = "getAllBrands")
    public Page<BrandDTO> getAll(@Argument int page, @Argument int size) {
        return brandService.getAll(page, size);
    }

    @QueryMapping(name = "getBrandById")
    public BrandDTO get(@Argument Long id) {
        return brandService.get(id);
    }

    @MutationMapping(name = "createBrand")
    public BrandDTO create(@Argument BrandDTO request) {
        return brandService.create(request);
    }   

    @MutationMapping(name = "updateBrand")
    public BrandDTO update(@Argument BrandDTO request, @Argument Long id) {
        return brandService.update(request, id);
    }   

    @MutationMapping(name = "deleteBrand")
    public Boolean delete(@Argument Long id) {
        brandService.delete(id);

        return true;
    }


    //Webflux Suscription
    @SubscriptionMapping 
    public Flux<BrandDTO> getAllBrandsFlux()
    {
        return brandService.getAllBrandsFlux();
    }

    @SubscriptionMapping 
    public Mono<BrandDTO> getMonoBrandFlux(@Argument Long id)
    {
        return brandService.getOneMono(id);
    }
}
