package br.com.postech.soat.order.core.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("OrderItem Tests")
class OrderItemTest {

    @Test
    @DisplayName("Should create an order item with all properties")
    void shouldCreateOrderItemWithAllProperties() {
        // Arrange
        UUID productId = UUID.randomUUID();
        String name = "Test Product";
        Integer quantity = 2;
        BigDecimal price = new BigDecimal("15.99");
        String category = "Test Category";
        Discount discount = new Discount(new BigDecimal("5.00"));

        // Act
        OrderItem orderItem = new OrderItem(
            productId,
            name,
            quantity,
            price,
            category,
            discount
        );

        // Assert
        assertNotNull(orderItem);
        assertNotNull(orderItem.getId());
        assertEquals(productId, orderItem.getProductId());
        assertEquals(name, orderItem.getName());
        assertEquals(quantity, orderItem.getQuantity());
        assertEquals(price, orderItem.getPrice());
        assertEquals(category, orderItem.getCategory());
        assertEquals(discount, orderItem.getDiscount());
    }

    @Test
    @DisplayName("Should create an order item without discount")
    void shouldCreateOrderItemWithoutDiscount() {
        // Arrange
        UUID productId = UUID.randomUUID();
        String name = "Test Product";
        Integer quantity = 1;
        BigDecimal price = new BigDecimal("10.00");
        String category = "Test Category";

        // Act
        OrderItem orderItem = new OrderItem(
            productId,
            name,
            quantity,
            price,
            category,
            null
        );

        // Assert
        assertNotNull(orderItem);
        assertNotNull(orderItem.getId());
        assertEquals(productId, orderItem.getProductId());
        assertEquals(name, orderItem.getName());
        assertEquals(quantity, orderItem.getQuantity());
        assertEquals(price, orderItem.getPrice());
        assertEquals(category, orderItem.getCategory());
        assertNull(orderItem.getDiscount());
    }
}