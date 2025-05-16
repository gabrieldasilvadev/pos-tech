package br.com.postech.soat.customer.core.domain.exeption;

import br.com.postech.soat.commons.domain.exeption.DomainException;

public class CustomerAlreadyExistsException extends DomainException {
    public CustomerAlreadyExistsException(String message) {
        super(message);
        setStatus(409);
    }
}