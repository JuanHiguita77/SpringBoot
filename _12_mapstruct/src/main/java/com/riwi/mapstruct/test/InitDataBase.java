package com.riwi.mapstruct.test;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.riwi.mapstruct.dto.GetProduct;
import com.riwi.mapstruct.entity.Product;
import com.riwi.mapstruct.mapper.ProductMapper;
import com.riwi.mapstruct.repository.ProductRepository;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class InitDataBase 
{
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Bean
    public CommandLineRunner testProductMapperCommand()
    {
        return args ->
        {
            List<Product> products = productRepository.findAll();

            System.out.println(products);

            //products.forEach(product -> System.out.println(product));
            products.forEach(System.out::println);

            System.out.println("Get Product");
           /*List<GetProduct> getProductList = products.stream().map(product -> productMapper.productGet(product))
            .peek(System.out::println)
            .collect(Collectors.toList()); */       

            List<GetProduct> getProductList = productMapper.toGetproductList(products);
            getProductList.forEach(System.out::println);
            
            System.out.println("Mapped Products: ");
            List<Product> mappedProducts = productMapper.toProductList(getProductList);
            mappedProducts.forEach(System.out::println);

        };
    }    
}
