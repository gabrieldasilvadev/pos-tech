package br.com.postech.soat.customer.core.domain.exception;

import br.com.postech.soat.commons.domain.exception.DomainException;

public class InvalidEmailException extends DomainException {
    public InvalidEmailException(String message) {
        super(message);
    }
}