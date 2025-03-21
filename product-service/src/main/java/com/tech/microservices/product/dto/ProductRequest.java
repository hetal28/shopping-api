package com.tech.microservices.product.dto;

import java.math.BigDecimal;

public record ProductRequest(String id, String name, String skuCode, String description, BigDecimal price) {
}
