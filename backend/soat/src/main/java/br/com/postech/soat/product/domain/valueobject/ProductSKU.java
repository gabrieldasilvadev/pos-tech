package br.com.postech.soat.product.domain.valueobject;


import br.com.postech.soat.product.domain.exception.InvalidSKUException;

public record ProductSKU(String value) {
    public ProductSKU {
        validate(value);
    }

    private void validate(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidSKUException("SKU não pode ser nulo");
        }
    }
}