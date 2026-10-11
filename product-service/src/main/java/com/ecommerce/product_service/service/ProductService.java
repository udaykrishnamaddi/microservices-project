package com.ecommerce.product_service.service;


import com.ecommerce.product_service.exception.ProductNotFoundException;
import com.ecommerce.product_service.model.Product;
import com.ecommerce.product_service.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts()
    {
        return productRepository.findAll();
    }

    public Product getProductById(Long id)
    {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Product existing = getProductById(id);
        existing.setName(updatedProduct.getName());
        existing.setDescription(updatedProduct.getDescription());
        existing.setPrice(updatedProduct.getPrice());
        existing.setQuantity(updatedProduct.getQuantity());
        return productRepository.save(existing);
    }

    public void deleteProduct(Long id) {
        Product existing = getProductById(id);
        productRepository.delete(existing);
    }

    public boolean isAvailable(Long id, int requestedQuantity) {
        Product product = getProductById(id);
        return product.getQuantity() >= requestedQuantity;
    }

    public void reduceStock(Long id, int quantityToReduce) {
        Product product = getProductById(id);
        if(product.getQuantity() < quantityToReduce) {
            throw new IllegalStateException("insufficient stock for product id: "+id);
        }
        product.setQuantity(product.getQuantity() - quantityToReduce);
        productRepository.save(product);
    }
}
