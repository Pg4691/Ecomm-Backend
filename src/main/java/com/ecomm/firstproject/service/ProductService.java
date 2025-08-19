package com.ecomm.firstproject.service;

import com.ecomm.firstproject.model.Product;
import com.ecomm.firstproject.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {


    private final ProductRepository productRepository;


    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + id));
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public String deleteProduct(Product product) {
        productRepository.delete(product);
        return "Product deleted ";
    }

}
