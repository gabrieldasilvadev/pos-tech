package br.com.postech.soat.customer.core.domain.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InvalidExceptionsTest {
    @Test
    void givenMessage_whenCreateInvalidEmailException_thenMessageIsSet() {
        InvalidEmailException ex = new InvalidEmailException("Email inválido");
        assertEquals("Email inválido", ex.getMessage());
    }

    @Test
    void givenMessage_whenCreateInvalidPhoneException_thenMessageIsSet() {
        InvalidPhoneException ex = new InvalidPhoneException("Telefone inválido");
        assertEquals("Telefone inválido", ex.getMessage());
    }

    @Test
    void givenMessage_whenCreateInvalidNameException_thenMessageIsSet() {
        InvalidNameException ex = new InvalidNameException("Nome inválido");
        assertEquals("Nome inválido", ex.getMessage());
    }

    @Test
    void givenMessage_whenCreateInvalidCpfException_thenMessageIsSet() {
        InvalidCpfException ex = new InvalidCpfException("CPF inválido");
        assertEquals("CPF inválido", ex.getMessage());
    }
}

