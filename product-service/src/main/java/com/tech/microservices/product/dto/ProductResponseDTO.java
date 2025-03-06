package com.tech.microservices.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductResponseDTO {

    private String id;
    private String name;
    private String skuCode;
    private String description;
    private BigDecimal price;
}
