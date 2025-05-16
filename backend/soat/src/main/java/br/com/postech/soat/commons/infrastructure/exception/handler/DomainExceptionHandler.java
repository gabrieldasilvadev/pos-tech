package br.com.postech.soat.commons.infrastructure.exception.handler;

import br.com.postech.soat.commons.infrastructure.exception.DomainException;
import br.com.postech.soat.commons.infrastructure.exception.NotFoundException;
import br.com.postech.soat.openapi.model.ErrorResponse;
import java.util.Collections;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DomainExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse()
            .status(404)
            .message("Entidade não encontrada")
            .error(Collections.singletonList(e.getMessage()));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResponse> handleDomainException(DomainException e) {
        ErrorResponse errorResponse = new ErrorResponse()
            .status(e.getStatus())
            .message("Requisição inválida")
            .error(Collections.singletonList(e.getMessage()));

        return ResponseEntity.status(HttpStatus.valueOf(e.getStatus())).body(errorResponse);
    }
}

