package br.com.postech.soat.customer.core.domain.exeption;

import br.com.postech.soat.common.domain.exeption.DomainException;

public class InvalidNameException extends DomainException {
    public InvalidNameException(String message) {
        super(message);
    }
}