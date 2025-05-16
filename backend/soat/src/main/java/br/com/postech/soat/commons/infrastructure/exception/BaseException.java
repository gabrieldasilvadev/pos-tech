package br.com.postech.soat.commons.infrastructure.exception;

public abstract class BaseException extends RuntimeException {

    private int status = 400;

    public BaseException(String message) {
        super(message);
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
