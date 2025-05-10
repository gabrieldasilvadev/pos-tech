package br.com.postech.soat.common.domain.exeption;

public abstract class DomainException extends RuntimeException {
    public DomainException(String message) {
        super(message);
    }
}
