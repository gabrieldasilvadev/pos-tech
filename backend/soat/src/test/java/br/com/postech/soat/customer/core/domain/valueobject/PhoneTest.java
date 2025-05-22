package br.com.postech.soat.customer.core.domain.valueobject;

import br.com.postech.soat.customer.core.domain.exception.InvalidPhoneException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Phone Tests")
class PhoneTest {

    @Test
    @DisplayName("Should create Phone successfully when a valid 10-digit value is provided")
    void givenValid10DigitValue_whenCreatePhone_thenSuccess() {
        // Act
        Phone phone = new Phone("1198765432");

        // Assert
        assertNotNull(phone);
        assertEquals("1198765432", phone.value());
    }

    @Test
    @DisplayName("Should create Phone successfully when a valid 11-digit value is provided")
    void givenValid11DigitValue_whenCreatePhone_thenSuccess() {
        // Act
        Phone phone = new Phone("11987654321");

        // Assert
        assertNotNull(phone);
        assertEquals("11987654321", phone.value());
    }

    @Test
    @DisplayName("Should normalize Phone by removing non-numeric characters")
    void givenFormattedPhone_whenCreatePhone_thenNormalizeValue() {
        // Act
        Phone phone = new Phone("(11) 98765-4321");

        // Assert
        assertNotNull(phone);
        assertEquals("11987654321", phone.value());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"123", "123456789", "123456789012", "abcdefghijk"})
    @DisplayName("Should throw InvalidPhoneException when invalid value is provided")
    void givenInvalidValue_whenCreatePhone_thenThrowInvalidPhoneException(String invalidValue) {
        // Act & Assert
        InvalidPhoneException exception = assertThrows(
            InvalidPhoneException.class,
            () -> new Phone(invalidValue)
        );

        assertNotNull(exception.getMessage());
    }
}