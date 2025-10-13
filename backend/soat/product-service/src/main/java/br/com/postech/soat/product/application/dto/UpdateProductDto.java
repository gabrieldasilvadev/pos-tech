package br.com.postech.soat.product.application.dto;

import java.math.BigDecimal;

public record UpdateProductDto(
    String name,
    BigDecimal price,
    String description,
    String image,
    String category
) {
}
