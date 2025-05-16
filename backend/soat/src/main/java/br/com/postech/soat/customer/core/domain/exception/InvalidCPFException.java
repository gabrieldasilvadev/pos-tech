package br.com.postech.soat.customer.core.domain.exception;

import br.com.postech.soat.commons.infrastructure.exception.DomainException;

public class InvalidCPFException extends DomainException {
    public InvalidCPFException(String message) {
        super(message);
    }
}