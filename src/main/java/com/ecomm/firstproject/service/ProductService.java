package com.ecomm.firstproject.service;

import com.ecomm.firstproject.model.Product;
import com.ecomm.firstproject.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Get all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Get product by ID
    public Product getProductById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + id));
    }

    // Get product by name
    public Product getProductByName(String productName) {
        return Optional.ofNullable(productRepository.findByProductName(productName))
                .orElseThrow(() -> new RuntimeException("Product not found with name " + productName));
    }

    // Get product by product-level SKU ID
    public Product getProductBySkuId(String skuId) {
        return Optional.ofNullable(productRepository.findByskuId(skuId))
                .orElseThrow(() -> new RuntimeException("Product not found with skuId " + skuId));
    }

    // Save product
    public Product saveProduct(Product product) {
        if (product.getSkus() != null && !product.getSkus().isEmpty()) {
            for (Product.Sku sku : product.getSkus()) {
                // If SKU prices are missing → fallback from Product
                if (sku.getMrp() == null) {
                    sku.setMrp(product.getMrp());
                }
                if (sku.getSellingPrice() == null) {
                    sku.setSellingPrice(product.getSellingPrice());
                }

                // Handle variants inside SKU
                if (sku.getVariants() != null && !sku.getVariants().isEmpty()) {
                    for (Product.Variant variant : sku.getVariants()) {
                        if (variant.getMrp() == null) {
                            variant.setMrp(sku.getMrp() != null ? sku.getMrp() : product.getMrp());
                        }
                        if (variant.getSellingPrice() == null) {
                            variant.setSellingPrice(sku.getSellingPrice() != null ? sku.getSellingPrice() : product.getSellingPrice());
                        }
                    }
                }
            }
        }

        return productRepository.save(product);
    }

    // Delete product
    public String deleteProduct(Product product) {
        productRepository.delete(product);
        return "Product deleted successfully";
    }
}
