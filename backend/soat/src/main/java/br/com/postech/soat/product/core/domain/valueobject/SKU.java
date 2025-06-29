package br.com.postech.soat.product.core.domain.valueobject;


import br.com.postech.soat.product.core.domain.exception.InvalidSKUException;

public record SKU (String value) {
    public SKU {
        validate(value);
    }

    private void validate(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidSKUException("SKU não pode ser nulo");
        }
    }
}