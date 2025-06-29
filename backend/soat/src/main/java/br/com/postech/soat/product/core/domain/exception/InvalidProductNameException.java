package br.com.postech.soat.product.core.domain.exception;

import br.com.postech.soat.commons.infrastructure.exception.BaseException;

public class InvalidProductNameException extends BaseException {
    public InvalidProductNameException(String message) {
        super(message);
    }
}