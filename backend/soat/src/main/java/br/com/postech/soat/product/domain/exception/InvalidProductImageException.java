package br.com.postech.soat.product.domain.exception;

import br.com.postech.soat.commons.infrastructure.exception.BaseException;

public class InvalidProductImageException extends BaseException {
    public InvalidProductImageException(String message) {
        super(message);
    }
}
