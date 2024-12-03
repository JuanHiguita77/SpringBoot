package com.juan.inventory_service.infraestructure.abstract_services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.juan.inventory_service.api.dto.ProductDTO;
import com.juan.inventory_service.infraestructure.services.generic_services.CreateService;
import com.juan.inventory_service.infraestructure.services.generic_services.DeleteService;
import com.juan.inventory_service.infraestructure.services.generic_services.GetAllService;
import com.juan.inventory_service.infraestructure.services.generic_services.GetService;
import com.juan.inventory_service.infraestructure.services.generic_services.UpdateService;

public interface IProductService extends GetAllService<ProductDTO>, GetService<ProductDTO, Long>, 
CreateService<ProductDTO, ProductDTO>, 
UpdateService<ProductDTO, ProductDTO, Long>, 
DeleteService<Long> 
{
    Page<ProductDTO> getProductsByCategory(String category, Pageable pageable);

    ProductDTO updateStock(int stock, long id);

    ProductDTO reciveNewShipment(int newStock, long id);
}
