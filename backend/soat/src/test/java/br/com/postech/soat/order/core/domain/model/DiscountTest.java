package br.com.postech.soat.order.core.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Discount Tests")
class DiscountTest {

    @Test
    @DisplayName("Should create a discount with a value")
    void shouldCreateDiscountWithValue() {
        // Arrange
        BigDecimal value = new BigDecimal("10.00");

        // Act
        Discount discount = new Discount(value);

        // Assert
        assertNotNull(discount);
        assertEquals(value, discount.getValue());
    }

    @Test
    @DisplayName("Should create a discount with zero value")
    void shouldCreateDiscountWithZeroValue() {
        // Arrange
        BigDecimal value = BigDecimal.ZERO;

        // Act
        Discount discount = new Discount(value);

        // Assert
        assertNotNull(discount);
        assertEquals(value, discount.getValue());
    }

    @Test
    @DisplayName("Should create a discount with negative value")
    void shouldCreateDiscountWithNegativeValue() {
        // Arrange
        BigDecimal value = new BigDecimal("-5.00");

        // Act
        Discount discount = new Discount(value);

        // Assert
        assertNotNull(discount);
        assertEquals(value, discount.getValue());
    }
}