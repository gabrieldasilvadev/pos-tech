package br.com.postech.soat.product.domain.valueobject;

import br.com.postech.soat.product.domain.enumtypes.Category;
import br.com.postech.soat.product.domain.exception.InvalidProductCategoryException;

public record ProductCategory(String value) {
    public ProductCategory{
        validate(value);
    }

    private void validate(String value) {
        try {
            Category.entryOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidProductCategoryException("Valor inválido para a categoria: " + value);
        }
    }

    public Category category() {
        return Category.entryOf(value);
    }
}
