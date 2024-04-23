package com.riwi.products.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.riwi.products.entities.Product;
import com.riwi.products.repositories.ProductRepository;
import com.riwi.products.service.abstract_service.IProductService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor //Constructor para las dependencias
public class ProductService implements IProductService
{
    @Autowired
    private final ProductRepository productRepository;

    @Override
    public List<Product> getAll() 
    {
        return this.productRepository.findAll();
    }

    @Override
    public Product save(Product product)
    {
        return this.productRepository.save(product);
    }

    @Override
    public void delete(Product product) 
    { 
        this.productRepository.delete(product);
    }

    @Override
    public Product findById(Long id) 
    {
        return this.productRepository.findById(id).orElse(null);
    }

    @Override
    public List<Product> search(String name) 
    {
        return this.productRepository.findByName(name);
    }

    @Override
    public Product update(Long id, Product product) 
    {
        this.productRepository.findById(id).orElseThrow();
        
        product.setId(id);
        return this.productRepository.save(product);
    }

}
