package br.com.postech.soat.order.core.domain.model;

import java.util.UUID;

public record DiscountId(
    UUID value
) {

    public static DiscountId of(UUID value) {
        return new DiscountId(value);
    }
}
