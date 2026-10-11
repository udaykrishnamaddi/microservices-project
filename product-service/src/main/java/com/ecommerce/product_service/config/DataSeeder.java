package com.ecommerce.product_service.config;

import com.ecommerce.product_service.model.Product;
import com.ecommerce.product_service.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataSeeder implements CommandLineRunner {

    private final ProductRepository productRepository;

    public DataSeeder(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) {

        if (productRepository.count() > 0){
            return;
        }
        productRepository.save(new Product(null, "Wireless Mouse", "Ergonomic wireless mouse", new BigDecimal("19.99"), 100));
        productRepository.save(new Product(null, "Mechanical Keyboard", "Rgb Mechanical Keyboard", new BigDecimal("59.99"), 50));
        productRepository.save(new Product(null, "USB-C Hub", "7-in-1 USB-C hub", new BigDecimal("29.99"), 0));

    }

}
