package com.ecomm.firstproject;

import com.ecomm.firstproject.controller.ProductController;
import com.ecomm.firstproject.model.Product;
import com.ecomm.firstproject.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private Product sampleProduct;
    private Product skuLevelProduct;
    private Product variantLevelProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

       // root level
        sampleProduct = new Product();
        sampleProduct.setId("1");
        sampleProduct.setProductName("Laptop");
        sampleProduct.setProductDescription("Controller test");
        sampleProduct.setMrp(BigDecimal.valueOf(2000));
        sampleProduct.setSellingPrice(BigDecimal.valueOf(1800));
        sampleProduct.setCategory("Electronics");
        sampleProduct.setCreationDate(new Date());
        sampleProduct.setUpdatedDate(new Date());
        sampleProduct.setSkuId("SKU-CTRL");

        // ------------------ SKU-Level Product ------------------
        Product.Sku sku1 = new Product.Sku("SKU-1", new BigDecimal("1500"), new BigDecimal("1400"), 20, null);
        Product.Sku sku2 = new Product.Sku("SKU-2", new BigDecimal("1600"), new BigDecimal("1500"), 10, null);

        skuLevelProduct = new Product();
        skuLevelProduct.setId("2");
        skuLevelProduct.setProductName("SKU Product");
        skuLevelProduct.setMrp(BigDecimal.valueOf(2000));
        skuLevelProduct.setSellingPrice(BigDecimal.valueOf(1800));
        skuLevelProduct.setSkus(Arrays.asList(sku1, sku2));
        skuLevelProduct.setSkuId("SKU-LEVEL");

        // ------------------ Variant-Level Product ------------------
        Product.Variant v1 = new Product.Variant("Red", new BigDecimal("120"), new BigDecimal("100"), 5);
        Product.Variant v2 = new Product.Variant("Blue", new BigDecimal("130"), new BigDecimal("110"), 3);

        Product.Sku skuWithVariants = new Product.Sku("SKU-1", new BigDecimal("1500"), new BigDecimal("1400"), 10,
                Arrays.asList(v1, v2));

        variantLevelProduct = new Product();
        variantLevelProduct.setId("3");
        variantLevelProduct.setProductName("Variant Product");
        variantLevelProduct.setMrp(BigDecimal.valueOf(2000));
        variantLevelProduct.setSellingPrice(BigDecimal.valueOf(1800));
        variantLevelProduct.setSkus(Arrays.asList(skuWithVariants));
        variantLevelProduct.setSkuId("SKU-VARIANT");
    }


    @Test
    void testGetAllProducts() {
        when(productService.getAllProducts()).thenReturn(Arrays.asList(sampleProduct));

        ResponseEntity<List<Product>> response = productController.getAllProducts();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("Laptop", response.getBody().get(0).getProductName());
    }

    @Test
    void testGetProductById() {
        when(productService.getProductById("1")).thenReturn(sampleProduct);

        ResponseEntity<Product> response = productController.getProductById("1");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Laptop", response.getBody().getProductName());
    }

    @Test
    void testCreateProduct() {
        when(productService.saveProduct(any(Product.class))).thenReturn(sampleProduct);

        ResponseEntity<String> response = productController.createProduct(sampleProduct);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Product created successfully", response.getBody());
        verify(productService).saveProduct(sampleProduct);
    }

    @Test
    void testUpdateProduct() {
        when(productService.getProductById("1")).thenReturn(sampleProduct);
        when(productService.saveProduct(any(Product.class))).thenReturn(sampleProduct);

        ResponseEntity<Product> response = productController.updateProduct("1", sampleProduct);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(sampleProduct, response.getBody());
        verify(productService).saveProduct(sampleProduct);
    }

    @Test
    void testDeleteProduct_NotFound() {
        when(productService.getProductById("1")).thenReturn(null);

        ResponseEntity<String> response = productController.deleteProduct("1");

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Product not found", response.getBody());

        verify(productService).getProductById("1");
        verify(productService, never()).deleteProduct(any());
    }

    // ------------------ SKU-Level Product Tests ------------------
    @Test
    void testGetSkuLevelProduct() {
        when(productService.getProductById("2")).thenReturn(skuLevelProduct);

        ResponseEntity<Product> response = productController.getProductById("2");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("SKU Product", response.getBody().getProductName());
        assertEquals(2, response.getBody().getSkus().size());
        assertNull(response.getBody().getSkus().get(0).getVariants()); // No variants
    }

    @Test
    void testCreateSkuLevelProduct() {
        when(productService.saveProduct(any(Product.class))).thenReturn(skuLevelProduct);

        ResponseEntity<String> response = productController.createProduct(skuLevelProduct);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Product created successfully", response.getBody());
        verify(productService).saveProduct(skuLevelProduct);
    }

    // ------------------ Variant-Level Product Tests ------------------
    @Test
    void testGetVariantLevelProduct() {
        when(productService.getProductById("3")).thenReturn(variantLevelProduct);

        ResponseEntity<Product> response = productController.getProductById("3");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Variant Product", response.getBody().getProductName());
        assertEquals(1, response.getBody().getSkus().size());
        assertEquals(2, response.getBody().getSkus().get(0).getVariants().size());
        assertEquals("Red", response.getBody().getSkus().get(0).getVariants().get(0).getName());
    }

    @Test
    void testCreateVariantLevelProduct() {
        when(productService.saveProduct(any(Product.class))).thenReturn(variantLevelProduct);

        ResponseEntity<String> response = productController.createProduct(variantLevelProduct);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Product created successfully", response.getBody());
        verify(productService).saveProduct(variantLevelProduct);
    }
}
