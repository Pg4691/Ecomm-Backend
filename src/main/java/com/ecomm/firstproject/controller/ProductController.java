package com.ecomm.firstproject.controller;

import com.ecomm.firstproject.model.Product;
import com.ecomm.firstproject.repository.ProductRepository;
import com.ecomm.firstproject.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private  ProductService productService;

    // Get all products
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts()) ;
    }

    @GetMapping("/product/id/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id){
        return ResponseEntity.ok(productService.getProductById(id));
    }
    @GetMapping("/product/name/{productName}")
    public ResponseEntity<Product> getProductByName(@PathVariable String productName) {
        Product product = productService.getProductByName(productName);

        if (product != null) {
            // ✅ Always recalc from SKUs/Variants
            BigDecimal effectiveMrp = product.getEffectiveMrp();
            BigDecimal effectiveSelling = product.getEffectiveSellingPrice();

            product.setMrp(effectiveMrp);
            product.setSellingPrice(effectiveSelling);

            return ResponseEntity.ok(product);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


        // Create product

    @PostMapping("/product")
    public ResponseEntity<String> createProduct(@RequestBody Product product) {
         productService.saveProduct(product);
         return ResponseEntity.ok("Product created sucessfully");
    }

    // Update product

    @PutMapping("/product/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable String id, @RequestBody Product productDetails) {
        Product product = productService.getProductById(id);

        if (product != null) {
            product.setProductName(productDetails.getProductName());
            product.setProductDescription(productDetails.getProductDescription());
            product.setMrp(productDetails.getMrp());
            product.setSellingPrice(productDetails.getSellingPrice());
            product.setCategory(productDetails.getCategory());
            product.setUpdatedDate(productDetails.getUpdatedDate());
            product.setSkus(productDetails.getSkus());

            productService.saveProduct(product);
            return ResponseEntity.ok( "Product updated succesfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Product not found");
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

















































































































//package com.ecomm.firstproject.controller;
//
//
//import com.ecomm.firstproject.model.Product;
//import com.ecomm.firstproject.repository.ProductRepository;
//import com.ecomm.firstproject.service.ProductService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api")
//public class ProductController {
//    private final ProductService productService;
//    public ProductController(ProductService productService) {
//        this.productService = productService;
//    }
//
//    @GetMapping("/products")
//    public ResponseEntity<List<Product>> getAllProducts(){
//        ProductController service;
//        return new ResponseEntity<>(productService.getAllProducts(),HttpStatus.OK);
//    }
//    @GetMapping("/product{id}")
//    public ResponseEntity<Product> getProduct(@PathVariable String id) {
//        Product product = productService.getProductById(id);
//        if (product != null) {
//            return new ResponseEntity<>(product, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//    @PostMapping("/product")
//    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
//        Product savedProduct = productService.saveProduct(product);
//        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
//    }
//    @Autowired
//    private ProductRepository productRepository;
//    @PutMapping("/product{id}")
//    public ResponseEntity<?> updateProduct(@PathVariable String id, @RequestBody Product productDetails) {
//        Optional<Product> productOptional = productRepository.findById(id);
//
//        if (productOptional.isPresent()) {
//            Product product = productOptional.get();
//
//            // Update fields
//            product.setProductName(productDetails.getProductName());
//            product.setProductDescription(productDetails.getProductDescription());
//            product.setMrp(productDetails.getMrp());
//            product.setSellingPrice(productDetails.getSellingPrice());
//            product.setCategory(productDetails.getCategory());
//            product.setUpdatedDate(productDetails.getUpdatedDate());
//
//            Product updatedProduct = productRepository.save(product);
//            return ResponseEntity.ok(updatedProduct);
//        } else {
//            return ResponseEntity.status(404).body("Product not found with id: " + id);
//        }
//    }
//    @DeleteMapping("/product{id}")
//    public ResponseEntity<Product> deleteProduct(@PathVariable String id) {
//        Optional<Product> product = productRepository.findById(id);
//
//        if (product.isPresent()) {
//            productRepository.delete(product.get());
//            return ResponseEntity.ok(product.get()); // return deleted product details
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//}


