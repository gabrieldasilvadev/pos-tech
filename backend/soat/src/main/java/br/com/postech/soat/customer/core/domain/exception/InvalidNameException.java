package br.com.postech.soat.customer.core.domain.exception;

import br.com.postech.soat.commons.infrastructure.exception.BaseException;

public class InvalidNameException extends BaseException {
    public InvalidNameException(String message) {
        super(message);
    }
}