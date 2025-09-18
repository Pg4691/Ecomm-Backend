package com.ecomm.firstproject.controller;

import com.ecomm.firstproject.model.Product;
import com.ecomm.firstproject.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Get all products
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // Get product by ID
    @GetMapping("/product/id/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Get product by name
    @GetMapping("/product/name/{productName}")
    public ResponseEntity<Product> getProductByName(@PathVariable String productName) {
        Product product = productService.getProductByName(productName);
        if (product != null) {
            // ✅ Removed effective price calculation
            return ResponseEntity.ok(product);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/product/{SkuId}")
    public ResponseEntity<Product>getProductBySkuId(@PathVariable String SkuId){
        Product product = productService.getProductBySkuId(SkuId);
        if(product !=null){
            return ResponseEntity.ok(product);

        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Create product
    @PostMapping("/product")
    public ResponseEntity<String> createProduct(@RequestBody Product product) {
        productService.saveProduct(product);
        return ResponseEntity.ok("Product created successfully");
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id, @RequestBody Product productDetails) {
        try {
            Product product = productService.getProductById(id);

            product.setProductName(productDetails.getProductName());
            product.setProductDescription(productDetails.getProductDescription());
            product.setMrp(productDetails.getMrp());
            product.setSellingPrice(productDetails.getSellingPrice());
            product.setCategory(productDetails.getCategory());
            product.setUpdatedDate(new Date()); // auto-update date
            product.setSkus(productDetails.getSkus());
            product.setSkuId(productDetails.getSkuId());
            // Product-level SKU

            Product updatedProduct = productService.saveProduct(product);

            return ResponseEntity.ok(updatedProduct); // return full product
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Product not found with id " + id);
        }
    }

    // Delete product
    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable String id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            productService.deleteProduct(product);
            return ResponseEntity.ok("Product deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
    }
}
