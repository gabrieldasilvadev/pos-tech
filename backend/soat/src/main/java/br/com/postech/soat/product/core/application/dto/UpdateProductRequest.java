package br.com.postech.soat.product.core.application.dto;

import java.math.BigDecimal;

public record UpdateProductRequest (
    String name,
    BigDecimal price,
    String description,
    String image,
    String category
) {
}
