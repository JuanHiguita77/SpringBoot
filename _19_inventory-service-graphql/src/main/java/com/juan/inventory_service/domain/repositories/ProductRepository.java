package com.juan.inventory_service.domain.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.juan.inventory_service.domain.entities.Product;


public interface ProductRepository extends JpaRepository<Product, Long> 
{
    Page<Product> getProductsByCategory(String category, Pageable pageable);
}
