package com.riwi.products.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.products.entities.Product;
import com.riwi.products.service.abstract_service.IProductService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
public class ProductController
{
    @Autowired
    private final IProductService IProductService;
    
    @GetMapping
    //Un generico que devuelve una lista de genericos tipo productos
    public ResponseEntity<List<Product>> list()
    {
        return ResponseEntity.ok(this.IProductService.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity<Product> add(@RequestBody Product product)
    { 
        return ResponseEntity.ok(this.IProductService.save(product));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id)
    {
        Product product = this.IProductService.findById(id);
        
        if (product != null) 
        {
            this.IProductService.delete(product);
            return ResponseEntity.ok(true);
        }
        
        return ResponseEntity.ok(false);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product)
    {
        return ResponseEntity.ok(this.IProductService.update(id, product));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id)
    {
        return ResponseEntity.ok(this.IProductService.findById(id));
    }

    @GetMapping("search/{name}")
    public ResponseEntity<List<Product>> findByName(@PathVariable String name)
    {
        return ResponseEntity.ok(this.IProductService.search(name));
    }
}