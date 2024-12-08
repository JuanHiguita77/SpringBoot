package com.juan.inventory_service.infraestructure.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.juan.inventory_service.api.dto.ProductDTO;
import com.juan.inventory_service.domain.entities.Product;
import com.juan.inventory_service.domain.repositories.ProductRepository;
import com.juan.inventory_service.infraestructure.abstract_services.IProductService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService implements IProductService {
    
    @Autowired
    private final ProductRepository productRepository;

    //Querys
    @Override
    public Page<ProductDTO> getAll(Pageable pageable) {

        Page<Product> products = productRepository.findAll(pageable);

        return products.map(product -> ProductDTO.builder()
            .id(product.getId())
            .name(product.getName())
            .category(product.getCategory())
            .price(product.getPrice())
            .stock(product.getStock())
            .build());
    }

    @Override
    public Page<ProductDTO> getProductsByCategory(String category, Pageable pageable) {
        Page<Product> products = productRepository.getProductsByCategory(category, pageable);

        
        return products.map(product -> ProductDTO.builder()
            .name(product.getName())
            .category(product.getCategory())
            .price(product.getPrice())
            .stock(product.getStock())
            .build());
    }

    @Override
    public ProductDTO getById(Long id) {
        return productRepository.findById(id).map(product -> ProductDTO.builder()
            .id(product.getId())
            .name(product.getName())
            .category(product.getCategory())
            .price(product.getPrice())
            .stock(product.getStock())
            .build()).orElseThrow(() -> new RuntimeException("Product not found!"));
    }

    //MUTATIONS
    //Update stock
    @Override
    public ProductDTO updateStock(int stock, long id) {

        Product productUpdated = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found!"));

        //Nuevo stock
        productUpdated.setStock(stock);

        productUpdated = productRepository.save(productUpdated);

        return ProductDTO.builder()
                        .id(productUpdated.getId())
                        .name(productUpdated.getName())
                        .category(productUpdated.getCategory())
                        .price(productUpdated.getPrice())
                        .stock(productUpdated.getStock())
                        .build();
    }

    public ProductDTO reciveNewShipment(int newStock, long id) {  

        Product productUpdated = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found!"));

        //New stock
        productUpdated.setStock(productUpdated.getStock() + newStock);

        productUpdated = productRepository.save(productUpdated);

        return ProductDTO.builder()
                        .id(productUpdated.getId())
                        .name(productUpdated.getName())
                        .category(productUpdated.getCategory())
                        .price(productUpdated.getPrice())
                        .stock(productUpdated.getStock())
                        .build();
    }


    @Override
    public ProductDTO create(ProductDTO request) {
        Product product = Product.builder()
            .name(request.getName())
            .category(request.getCategory())
            .price(request.getPrice())
            .stock(request.getStock())
            .build();

        product = productRepository.save(product);

        return ProductDTO.builder()
            .id(product.getId())
            .name(product.getName())
            .category(product.getCategory())
            .price(product.getPrice())
            .stock(product.getStock())
            .build();
    }  

    @Override
    public ProductDTO update(ProductDTO request, Long id) {
        Product productUpdated = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found!")); 

        productUpdated.builder()
        .name(request.getName())
        .category(request.getCategory())
        .price(request.getPrice())
        .stock(request.getStock())
        .build();

        productUpdated = productRepository.save(productUpdated);

        return ProductDTO.builder()
                        .id(productUpdated.getId())
                        .name(productUpdated.getName())
                        .category(productUpdated.getCategory())
                        .price(productUpdated.getPrice())
                        .stock(productUpdated.getStock())
                        .build();
    }

    @Override
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Product with " + id + " dont exist.");
        }
        productRepository.deleteById(id);
    }

}
