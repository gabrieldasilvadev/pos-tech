package br.com.postech.soat.customer.core.domain.valueobject;

import br.com.postech.soat.customer.core.domain.exeption.InvalidNameException;

public record Name(String value) {
    public Name {
        validate(value);
    }

    private void validate(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidNameException("Nome não pode ser vazio");
        }
    }
}