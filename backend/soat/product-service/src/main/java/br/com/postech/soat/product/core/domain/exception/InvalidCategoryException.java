package br.com.postech.soat.product.core.domain.exception;

import br.com.postech.soat.commons.infrastructure.exception.UnprocessableEntityException;

public class InvalidCategoryException extends UnprocessableEntityException {
    public InvalidCategoryException(String category) {
        super("Invalid category - " + category);
    }
}