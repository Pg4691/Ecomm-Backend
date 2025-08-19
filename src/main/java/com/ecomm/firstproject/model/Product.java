package com.ecomm.firstproject.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;

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


}
