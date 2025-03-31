package riwi.lastfilter.spring.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import riwi.lastfilter.spring.domain.entities.Product;
import riwi.lastfilter.spring.domain.repositories.ProductRepository;

@Component
@AllArgsConstructor
@Slf4j
public class DatabaseSeed implements CommandLineRunner {
    @Autowired
    private final ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
       log.info("Seeding database last filter"); 

        if (this.productRepository.count() > 0) return;
       
       Product product1 = Product.builder().name("product 1").price(1000.1).build();
       Product product2 = Product.builder().name("product 2").price(1000.2).build();
       Product product3 = Product.builder().name("product 3").price(1000.3).build();
       Product product4 = Product.builder().name("product 4").price(1000.4).build();
       Product product5 = Product.builder().name("product 5").price(1000.5).build();
       Product product6 = Product.builder().name("product 6").price(1000.6).build();
       Product product7 = Product.builder().name("product 7").price(1000.7).build();
       Product product8 = Product.builder().name("product 8").price(1000.8).build();
       Product product9 = Product.builder().name("product 9").price(1000.9).build();
       Product product10 = Product.builder().name("product 10").price(1000.10).build();

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        productRepository.save(product4);
        productRepository.save(product5);
        productRepository.save(product6);
        productRepository.save(product7);
        productRepository.save(product8);
        productRepository.save(product9);
        productRepository.save(product10);

    }
}