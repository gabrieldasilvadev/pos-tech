package br.com.postech.soat.customer.core.domain.exeption;

import br.com.postech.soat.commons.domain.exeption.DomainException;

public class InvalidCPFException extends DomainException {
    public InvalidCPFException(String message) {
        super(message);
    }
}