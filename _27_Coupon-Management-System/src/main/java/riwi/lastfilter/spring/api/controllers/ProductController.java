package riwi.lastfilter.spring.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import riwi.lastfilter.spring.domain.entities.Product;
import riwi.lastfilter.spring.infrastructure.abstrasct_services.interface_services.IProductService;


@RestController
@RequestMapping(path = "/products")
@AllArgsConstructor
public class ProductController {

    @Autowired
    private final IProductService productService;

     @GetMapping
    public ResponseEntity<Page<Product>> getAllBooks(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        PageRequest pagination = PageRequest.of(page-1, size);
        return ResponseEntity.ok(this.productService.findAll(pagination));
    }

    
    
}
