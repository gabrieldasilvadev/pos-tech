package br.com.postech.soat.customer.core.domain.valueobject;

import br.com.postech.soat.customer.core.domain.exception.InvalidEmailException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Email Tests")
class EmailTest {

    @Test
    @DisplayName("Should create Email successfully when valid value is provided")
    void givenValidValue_whenCreateEmail_thenSuccess() {
        // Act
        Email email = new Email("test@example.com");

        // Assert
        assertNotNull(email);
        assertEquals("test@example.com", email.value());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {
        "testeexample.com", // without @
        "teste@", // without domain
        "@example.com", // without user
        "teste@example", // without TLD
        "teste@.com", // invalid domain
        "teste@example.", // empty TLD
        "teste@example.c" // TLD too short
    })
    @DisplayName("Should throw InvalidEmailException when invalid value is provided")
    void givenInvalidValue_whenCreateEmail_thenThrowInvalidEmailException(String invalidValue) {
        // Act & Assert
        InvalidEmailException exception = assertThrows(
            InvalidEmailException.class,
            () -> new Email(invalidValue)
        );

        assertNotNull(exception.getMessage());
    }
}