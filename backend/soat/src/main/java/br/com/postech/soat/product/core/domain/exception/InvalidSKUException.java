package br.com.postech.soat.product.core.domain.exception;

import br.com.postech.soat.commons.infrastructure.exception.BaseException;

public class InvalidSKUException extends BaseException {
    public InvalidSKUException(String message) {
        super(message);
    }
}