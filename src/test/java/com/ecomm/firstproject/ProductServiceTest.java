package com.ecomm.firstproject;

import com.ecomm.firstproject.model.Product;
import com.ecomm.firstproject.repository.ProductRepository;
import com.ecomm.firstproject.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    // Fake repository
    @Mock
    private ProductRepository productRepository;

    // Service we are testing
    @InjectMocks
    private ProductService productService;

    private Product sampleProduct;

    @BeforeEach
    void setUp() {
        // Start Mockito (to enable @Mock and @InjectMocks)
        MockitoAnnotations.openMocks(this);

        // Create a sample product to reuse in tests
        sampleProduct = new Product();
        sampleProduct.setId("1");
        sampleProduct.setProductName("Phone");
        sampleProduct.setProductDescription("Test phone");
        sampleProduct.setMrp(BigDecimal.valueOf(1000));
        sampleProduct.setSellingPrice(BigDecimal.valueOf(900));
        sampleProduct.setCategory("Electronics");
        sampleProduct.setCreationDate(new Date());
        sampleProduct.setUpdatedDate(new Date());
        sampleProduct.setSkuId("SKU-123");
    }

    @Test
    void testGetAllProducts() {
        // Arrange (what repository should return)
        when(productRepository.findAll()).thenReturn(Arrays.asList(sampleProduct));

        // Act
        var result = productService.getAllProducts();

        // Assert
        assertEquals(1, result.size());
        assertEquals("Phone", result.get(0).getProductName());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetProductById_found() {
        when(productRepository.findById("1")).thenReturn(Optional.of(sampleProduct));

        Product found = productService.getProductById("1");

        assertNotNull(found);
        assertEquals("Phone", found.getProductName());
    }

    @Test
    void testGetProductById_notFound() {
        when(productRepository.findById("2")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> productService.getProductById("2"));

        assertTrue(ex.getMessage().contains("Product not found"));
    }

    @Test
    void testSaveProduct() {
        when(productRepository.save(sampleProduct)).thenReturn(sampleProduct);

        Product saved = productService.saveProduct(sampleProduct);

        assertEquals("Phone", saved.getProductName());
        verify(productRepository).save(sampleProduct);
    }

    @Test
    void testGetProductBySkuId() {
        when(productRepository.findByskuId("SKU-123")).thenReturn(sampleProduct);

        Product found = productService.getProductBySkuId("SKU-123");

        assertEquals("SKU-123", found.getSkuId());
    }
}
