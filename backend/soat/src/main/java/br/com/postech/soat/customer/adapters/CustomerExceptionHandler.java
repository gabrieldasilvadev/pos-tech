package br.com.postech.soat.customer.adapters;

import br.com.postech.soat.common.domain.exeption.DomainException;
import br.com.postech.soat.customer.core.domain.exeption.CustomerAlreadyExistsException;
import br.com.postech.soat.openapi.model.ErrorResponse;
import java.util.Collections;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "br.com.postech.soat.customer")
public class CustomerExceptionHandler {

    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleCustomerAlreadyExistsException(CustomerAlreadyExistsException e) {
        ErrorResponse errorResponse = new ErrorResponse()
            .status(409)
            .message("Cliente já existente")
            .error(Collections.singletonList(e.getMessage()));

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResponse> handleDomainException(DomainException e) {
        ErrorResponse errorResponse = new ErrorResponse()
            .status(400)
            .message("Requisição inválida")
            .error(Collections.singletonList(e.getMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse()
            .status(500)
            .message("Erro interno no servidor")
            .error(Collections.singletonList("Ocorreu um erro inesperado. Por favor, tente novamente mais tarde."));

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
