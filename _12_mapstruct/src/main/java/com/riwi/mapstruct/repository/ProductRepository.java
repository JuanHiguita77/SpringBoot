package com.riwi.mapstruct.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.riwi.mapstruct.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>
{

}
