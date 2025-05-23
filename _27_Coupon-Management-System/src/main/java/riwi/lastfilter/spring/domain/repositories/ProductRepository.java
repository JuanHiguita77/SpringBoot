package riwi.lastfilter.spring.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import riwi.lastfilter.spring.domain.entities.Product;

@Repository
public interface ProductRepository  extends JpaRepository<Product, Integer> {
    
}
