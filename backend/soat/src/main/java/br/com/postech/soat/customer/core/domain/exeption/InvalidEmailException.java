package br.com.postech.soat.customer.core.domain.exeption;

import br.com.postech.soat.commons.domain.exeption.DomainException;

public class InvalidEmailException extends DomainException {
    public InvalidEmailException(String message) {
        super(message);
    }
}