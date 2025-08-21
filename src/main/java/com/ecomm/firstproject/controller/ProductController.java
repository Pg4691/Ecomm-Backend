package com.ecomm.firstproject.controller;

import com.ecomm.firstproject.model.Product;
import com.ecomm.firstproject.repository.ProductRepository;
import com.ecomm.firstproject.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    // Get all products
    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/product/{id}")
    public Product getProduct(@PathVariable String id){
        return productService.getProductById(id);
    }

    // Create product
    @PostMapping("/product")
    public String createProduct(@RequestBody Product product) {
         productService.saveProduct(product);
         return "Product created sucessfully";
    }

    // Update product
    @PutMapping("/product/{id}")
    public Object updateProduct(@PathVariable String id, @RequestBody Product productDetails) {
        Product product = productService.getProductById(id);

        if (product != null) {
            product.setProductName(productDetails.getProductName());
            product.setProductDescription(productDetails.getProductDescription());
            product.setMrp(productDetails.getMrp());
            product.setSellingPrice(productDetails.getSellingPrice());
            product.setCategory(productDetails.getCategory());
            product.setUpdatedDate(productDetails.getUpdatedDate());

             productService.saveProduct(product);
            return "Product updated succesfully";
        }
        return "Product not found";
    }

    // Delete product
    @DeleteMapping("/product/{id}")
    public String deleteProduct(@PathVariable String id) {
        Product product = productService.getProductById(id);

        if (product != null) {
            productService.deleteProduct(product);
            return "Product deleted successfully!";
        }
        return "Product not found!";
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


