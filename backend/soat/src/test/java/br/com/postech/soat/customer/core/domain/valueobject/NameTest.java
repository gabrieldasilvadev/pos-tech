package br.com.postech.soat.customer.core.domain.valueobject;

import br.com.postech.soat.customer.core.domain.exception.InvalidNameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Tests for Name")
class NameTest {

    @Test
    @DisplayName("Should successfully create Name when a valid value is provided")
    void givenValidValue_whenCreateName_thenSuccess() {
        // Act
        Name name = new Name("João Silva");

        // Assert
        assertNotNull(name);
        assertEquals("João Silva", name.value());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  "})
    @DisplayName("Should throw InvalidNameException when an invalid value is provided")
    void givenInvalidValue_whenCreateName_thenThrowInvalidNameException(String invalidValue) {
        // Act & Assert
        InvalidNameException exception = assertThrows(
            InvalidNameException.class,
            () -> new Name(invalidValue)
        );

        assertEquals("Nome não pode ser vazio", exception.getMessage());
    }
}