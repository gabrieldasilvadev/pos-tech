package br.com.postech.soat.commons.infrastructure.exception.handler;

import br.com.postech.soat.commons.infrastructure.exception.BaseException;
import br.com.postech.soat.commons.infrastructure.exception.NotFoundException;
import br.com.postech.soat.openapi.model.ErrorResponse;
import java.util.Collections;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse()
            .status(404)
            .message("Entidade não encontrada")
            .error(Collections.singletonList(e.getMessage()));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleDomainException(BaseException e) {
        ErrorResponse errorResponse = new ErrorResponse()
            .status(e.getStatus())
            .message("Requisição inválida")
            .error(Collections.singletonList(e.getMessage()));

        return ResponseEntity.status(HttpStatus.valueOf(e.getStatus())).body(errorResponse);
    }
}

