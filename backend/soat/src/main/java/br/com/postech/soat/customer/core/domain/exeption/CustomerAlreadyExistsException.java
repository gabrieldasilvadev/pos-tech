package br.com.postech.soat.customer.core.domain.exeption;

import br.com.postech.soat.common.domain.exeption.DomainException;

public class CustomerAlreadyExistsException extends DomainException {
    public CustomerAlreadyExistsException(String message) {
        super(message);
    }
}