package br.com.postech.soat.order.core.domain.model;

import java.math.BigDecimal;

public record Discount(
    DiscountId id,
    BigDecimal value
) {
}
