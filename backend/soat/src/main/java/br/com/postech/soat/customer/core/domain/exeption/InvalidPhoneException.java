package br.com.postech.soat.customer.core.domain.exeption;

import br.com.postech.soat.commons.domain.exeption.DomainException;

public class InvalidPhoneException extends DomainException {
    public InvalidPhoneException(String message) {
        super(message);
    }
}