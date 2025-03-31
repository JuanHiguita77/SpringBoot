package com.juan.GraphQLCrud.domain.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.juan.GraphQLCrud.domain.entities.Model;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long>{
    Model findByName(String name);

    @Query("SELECT m FROM Model m WHERE m.brand.id = :brandId")
    Page<Model> findModelsByBrandId(@Param("brandId") Long brandId, Pageable pageable); 
}
