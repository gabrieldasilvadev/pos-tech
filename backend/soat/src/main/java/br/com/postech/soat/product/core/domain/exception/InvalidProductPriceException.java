package br.com.postech.soat.product.core.domain.exception;

import br.com.postech.soat.commons.infrastructure.exception.BaseException;

public class InvalidProductPriceException extends BaseException {
    public InvalidProductPriceException(String message){
        super(message);
    }
}
