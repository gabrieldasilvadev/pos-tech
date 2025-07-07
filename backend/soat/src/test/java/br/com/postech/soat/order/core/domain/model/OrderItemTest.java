package br.com.postech.soat.order.core.domain.model;

import br.com.postech.soat.order.domain.vo.Discount;
import br.com.postech.soat.order.domain.entity.OrderItem;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("OrderItem Tests")
class OrderItemTest {

    @Test
    @DisplayName("Should create an order item with all properties")
    void shouldCreateOrderItemWithAllProperties() {
        UUID productId = UUID.randomUUID();
        String name = "Test Product";
        Integer quantity = 2;
        BigDecimal price = new BigDecimal("15.99");
        String category = "Test Category";
        Discount discount = new Discount(new BigDecimal("5.00"));

        OrderItem orderItem = new OrderItem(
            productId,
            name,
            quantity,
            price,
            category,
            discount
        );

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
        UUID productId = UUID.randomUUID();
        String name = "Test Product";
        Integer quantity = 1;
        BigDecimal price = new BigDecimal("10.00");
        String category = "Test Category";

        OrderItem orderItem = new OrderItem(
            productId,
            name,
            quantity,
            price,
            category,
            null
        );

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