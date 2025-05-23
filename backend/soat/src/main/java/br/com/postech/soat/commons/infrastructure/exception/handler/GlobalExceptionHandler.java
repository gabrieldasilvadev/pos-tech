package br.com.postech.soat.commons.infrastructure.exception.handler;

import br.com.postech.soat.commons.infrastructure.exception.BaseException;
import br.com.postech.soat.commons.infrastructure.exception.NotFoundException;
import br.com.postech.soat.commons.infrastructure.exception.ResourceConflictException;
import br.com.postech.soat.openapi.model.ErrorResponseDto;
import java.util.Collections;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNotFoundException(NotFoundException e) {
        ErrorResponseDto errorResponse = new ErrorResponseDto()
            .status(404)
            .message("Entidade não encontrada")
            .error(Collections.singletonList(e.getMessage()));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(ResourceConflictException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceConflictException(ResourceConflictException e) {
        ErrorResponseDto errorResponse = new ErrorResponseDto()
            .status(409)
            .message("Conflito de recursos")
            .error(Collections.singletonList(e.getMessage()));

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponseDto> handleDomainException(BaseException e) {
        ErrorResponseDto errorResponse = new ErrorResponseDto()
            .status(400)
            .message("Requisição inválida")
            .error(Collections.singletonList(e.getMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}

