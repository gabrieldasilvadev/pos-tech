package br.com.postech.soat.customer.core.domain.exception;

import br.com.postech.soat.commons.infrastructure.exception.BaseException;

public class InvalidEmailException extends BaseException {
    public InvalidEmailException(String message) {
        super(message);
    }
}