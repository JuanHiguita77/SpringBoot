package com.juan.GraphQLCrud.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.juan.GraphQLCrud.domain.entities.Model;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long>{
    Model findByName(String name);
}
