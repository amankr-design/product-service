package com.aman.ecommerce.productservice.dto;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto implements Serializable {

    private Long id;

    @NotBlank(message = "Product name is required")
    private String name;

    @NotBlank(message = " Description is  required")
    private String description;

    @NotNull(message = "Price must be grater than 0")
    @DecimalMin(value = "0.01", message ="Price must be greater than 0")
    private Double price;

    @NotNull(message =  " Quantity is required")
    @Min(value = 1, message = "Quantity must be greater than 0")
    private Integer quantity;

}

