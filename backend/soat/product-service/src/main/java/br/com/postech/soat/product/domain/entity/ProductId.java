package br.com.postech.soat.product.domain.entity;

import br.com.postech.soat.commons.domain.Identifier;
import java.util.UUID;

public class ProductId extends Identifier {

    protected ProductId(UUID value) {
        super(value);
    }

    public static ProductId generate() {
        return new ProductId(UUID.randomUUID());
    }

    public static ProductId of(UUID value) {
        return new ProductId(value);
    }

    public static ProductId of(String value) {
        return new ProductId(UUID.fromString(value));
    }
}