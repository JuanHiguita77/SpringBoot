package riwi.lastfilter.spring.infrastructure.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import riwi.lastfilter.spring.domain.entities.Product;
import riwi.lastfilter.spring.domain.repositories.ProductRepository;
import riwi.lastfilter.spring.infrastructure.abstrasct_services.interface_services.IProductService;

@Service
@AllArgsConstructor
@Transactional
public class ProductService implements IProductService {

    @Autowired
    private final ProductRepository repository;

    @Override
    public Page<Product> findAll(PageRequest request) {
       return repository.findAll(request);
    }
}
