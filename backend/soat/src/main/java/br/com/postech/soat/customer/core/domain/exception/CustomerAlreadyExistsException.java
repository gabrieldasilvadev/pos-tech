package br.com.postech.soat.customer.core.domain.exception;

import br.com.postech.soat.commons.infrastructure.exception.BaseException;

public class CustomerAlreadyExistsException extends BaseException {
    public CustomerAlreadyExistsException(String message) {
        super(message);
        setStatus(409);
    }
}