package br.com.postech.soat.customer.core.domain.exception;

import br.com.postech.soat.commons.infrastructure.exception.DomainException;

public class InvalidPhoneException extends DomainException {
    public InvalidPhoneException(String message) {
        super(message);
    }
}