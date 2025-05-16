package br.com.postech.soat.customer.core.domain.exception;

import br.com.postech.soat.commons.domain.exception.DomainException;

public class InvalidNameException extends DomainException {
    public InvalidNameException(String message) {
        super(message);
    }
}