package com.juan.GraphQLCrud.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.juan.GraphQLCrud.domain.entities.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long>{
    Brand findByName(String name);
}
