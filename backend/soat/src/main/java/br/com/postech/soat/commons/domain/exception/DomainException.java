package br.com.postech.soat.commons.domain.exception;

public abstract class DomainException extends RuntimeException {

    private int status = 400;

    public DomainException(String message) {
        super(message);
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
