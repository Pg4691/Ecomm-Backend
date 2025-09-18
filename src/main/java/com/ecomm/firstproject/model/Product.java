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

    private List<Sku> skus;

    // ------------ Inner Classes ------------

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Sku {
        private String skuCode;
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

    // ------------ Utility Methods ------------
    public BigDecimal getEffectiveSellingPrice() {
        if (skus != null && !skus.isEmpty()) {
            // If product has SKUs
            return skus.stream()
                    .flatMap(sku -> {
                        if (sku.getVariants() != null && !sku.getVariants().isEmpty()) {
                            return sku.getVariants().stream().map(Variant::getSellingPrice);
                        } else {
                            return java.util.stream.Stream.of(sku.getSellingPrice());
                        }
                    })
                    .min(BigDecimal::compareTo)
                    .orElse(sellingPrice); // fallback
        }
        return sellingPrice; // no SKUs, use product price
    }

    public BigDecimal getEffectiveMrp() {
        if (skus != null && !skus.isEmpty()) {
            // If product has SKUs
            return skus.stream()
                    .flatMap(sku -> {
                        if (sku.getVariants() != null && !sku.getVariants().isEmpty()) {
                            return sku.getVariants().stream().map(Variant::getMrp);
                        } else {
                            return java.util.stream.Stream.of(sku.getMrp());
                        }
                    })
                    .min(BigDecimal::compareTo)
                    .orElse(mrp); // fallback
        }
        return mrp; // no SKUs, use product price
    }
}