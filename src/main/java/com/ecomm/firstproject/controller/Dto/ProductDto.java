package com.ecomm.firstproject.controller.Dto;

import javax.validation.constraints.NegativeOrZero;
import javax.validation.constraints.NotBlank;

public class ProductDto {

@NotBlank(message = "Proct name should not be null")
    private String productName;
}
