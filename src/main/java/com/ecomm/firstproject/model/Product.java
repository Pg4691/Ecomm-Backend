package com.ecomm.firstproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    private String id;

    private String productName;
    private String productDescription;
    private BigDecimal mrp;
    private BigDecimal sellingPrice;
    private String category;
    private Date creationDate;
    private Date updatedDate;
    private Integer stockQuantity;

    private List<Sku> skus;
    private String skuId;

    // ------------ Inner Classes ------------

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Sku {

        private String skuName;
        private BigDecimal mrp;
        private BigDecimal sellingPrice;
        private Integer stockQuantity;
        private List<Variant> variants;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Variant {
        private String name;
        private BigDecimal mrp;
        private BigDecimal sellingPrice;
        private Integer stockQuantity;
    }
}