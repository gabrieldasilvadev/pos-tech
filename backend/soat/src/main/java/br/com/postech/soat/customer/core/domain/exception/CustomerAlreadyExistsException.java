package br.com.postech.soat.customer.core.domain.exception;

import br.com.postech.soat.commons.domain.exception.DomainException;

public class CustomerAlreadyExistsException extends DomainException {
    public CustomerAlreadyExistsException(String message) {
        super(message);
        setStatus(409);
    }
}