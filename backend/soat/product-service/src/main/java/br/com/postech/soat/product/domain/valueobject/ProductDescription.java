package br.com.postech.soat.product.domain.valueobject;

import br.com.postech.soat.product.domain.exception.InvalidProductNameException;

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
