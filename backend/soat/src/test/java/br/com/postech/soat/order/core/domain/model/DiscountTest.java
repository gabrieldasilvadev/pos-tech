package br.com.postech.soat.order.core.domain.model;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Discount Tests")
class DiscountTest {

    @Test
    @DisplayName("Should create a discount with a value")
    void shouldCreateDiscountWithValue() {
        BigDecimal value = new BigDecimal("10.00");

        Discount discount = new Discount(value);

        assertNotNull(discount);
        assertEquals(value, discount.getValue());
    }

    @Test
    @DisplayName("Should create a discount with zero value")
    void shouldCreateDiscountWithZeroValue() {
        BigDecimal value = BigDecimal.ZERO;

        Discount discount = new Discount(value);

        assertNotNull(discount);
        assertEquals(value, discount.getValue());
    }

    @Test
    @DisplayName("Should create a discount with negative value")
    void shouldCreateDiscountWithNegativeValue() {
        BigDecimal value = new BigDecimal("-5.00");

        Discount discount = new Discount(value);

        assertNotNull(discount);
        assertEquals(value, discount.getValue());
    }
}