package br.com.postech.soat.order.core.domain.model;

import java.math.BigDecimal;

public record OrderItem(
    String name,
    Integer quantity,
    BigDecimal price,
    String category,
    Discount discount
) {
}
