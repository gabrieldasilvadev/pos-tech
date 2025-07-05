package br.com.postech.soat.customer.domain.valueobject;

import br.com.postech.soat.customer.domain.exception.InvalidCpfException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("CPF Tests")
class CPFTest {

    @Test
    @DisplayName("Should create CPF successfully when valid value is provided")
    void givenValidValue_whenCreateCPF_thenSuccess() {
        // Act
        CPF cpf = new CPF("12345678901");

        // Assert
        assertNotNull(cpf);
        assertEquals("12345678901", cpf.value());
    }

    @Test
    @DisplayName("Should normalize CPF by removing non-numeric characters")
    void givenFormattedCPF_whenCreateCPF_thenNormalizeValue() {
        // Act
        CPF cpf = new CPF("123.456.789-01");

        // Assert
        assertNotNull(cpf);
        assertEquals("12345678901", cpf.value());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"123", "1234567890", "123456789012", "abcdefghijk"})
    @DisplayName("Should throw InvalidCpfException when invalid value is provided")
    void givenInvalidValue_whenCreateCPF_thenThrowInvalidCpfException(String invalidValue) {
        // Act & Assert
        InvalidCpfException exception = assertThrows(
            InvalidCpfException.class,
            () -> new CPF(invalidValue)
        );

        assertNotNull(exception.getMessage());
    }
}