package br.com.postech.soat.product.core.domain.valueobject;

import br.com.postech.soat.product.core.domain.exception.InvalidProductNameException;
import br.com.postech.soat.product.core.domain.exception.InvalidProductPriceException;

public record ProductDescription(String value) {
    public ProductDescription {
        validate(value);
    }

    private void validate(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidProductNameException("A descrição do produto não pode ser nula");
        }
    }
}
