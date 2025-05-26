package br.com.postech.soat.product.core.exception;

import br.com.postech.soat.commons.infrastructure.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidCategoryException extends BaseException {
    public InvalidCategoryException(String message) {
        super(message);
    }
}
