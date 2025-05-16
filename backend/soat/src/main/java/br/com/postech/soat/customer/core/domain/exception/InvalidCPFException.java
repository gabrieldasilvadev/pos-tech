package br.com.postech.soat.customer.core.domain.exception;

import br.com.postech.soat.commons.infrastructure.exception.BaseException;

public class InvalidCPFException extends BaseException {
    public InvalidCPFException(String message) {
        super(message);
    }
}