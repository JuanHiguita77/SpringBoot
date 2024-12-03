package com.juan.inventory_service.api.controllers;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.juan.inventory_service.api.dto.ProductDTO;
import com.juan.inventory_service.infraestructure.abstract_services.IProductService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/products")
public class ProductGraphQLController {
    
    private final IProductService productService;

    //QUERYS
    //Get all stock and by category
    @QueryMapping
    public Page<ProductDTO> getAll(@Argument int page, @Argument int size) 
    {
        Pageable pageable = PageRequest.of(page - 1, size);
        
        return productService.getAll(pageable);
    }

    @QueryMapping
    public Page<ProductDTO> getProductsByCategory(@Argument String category, @Argument int page, @Argument int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        return productService.getProductsByCategory(category, pageable);
    }

    @QueryMapping
    public ProductDTO getById(@Argument Long id) {
        return productService.getById(id);
    }

    //MUTATIONS
    
    @MutationMapping
    public ProductDTO create(@Argument ProductDTO request) {
        return productService.create(request);
    }

    @MutationMapping
    public ProductDTO updateStock(@Argument int stock, @Argument Long id){
        return productService.updateStock(stock, id);
    }

    @MutationMapping
    public ProductDTO receiveNewShipment(@Argument int newStock, @Argument Long id){
        return productService.reciveNewShipment(newStock, id);
    }

    @MutationMapping
    public ProductDTO update(@Argument ProductDTO request, @Argument Long id) {
        return productService.update(request, id);
    }

    @MutationMapping
    public Boolean delete(@Argument Long id) {
        try {
            productService.delete(id); // Llama al servicio para eliminar
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
