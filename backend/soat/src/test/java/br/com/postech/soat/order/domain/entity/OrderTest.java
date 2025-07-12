package br.com.postech.soat.order.domain.entity;

import br.com.postech.soat.customer.domain.valueobject.CustomerId;
import br.com.postech.soat.order.domain.valueobject.Discount;
import br.com.postech.soat.order.domain.valueobject.Observation;
import br.com.postech.soat.order.domain.entity.Order;
import br.com.postech.soat.order.domain.entity.OrderItem;
import br.com.postech.soat.order.domain.entity.OrderStatus;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Order Tests")
class OrderTest {

    @Test
    @DisplayName("Should create an order with received status")
    void shouldCreateOrderWithReceivedStatus() {
        CustomerId customerId = new CustomerId(UUID.randomUUID());
        List<OrderItem> orderItems = new ArrayList<>();
        List<Discount> discounts = new ArrayList<>();
        List<Observation> observations = new ArrayList<>();

        Order order = Order.receive(customerId, orderItems, discounts, observations);

        assertNotNull(order);
        assertEquals(OrderStatus.RECEIVED, order.getStatus());
        assertEquals(customerId, order.getCustomerId());
        assertEquals(orderItems, order.getOrderItems());
        assertEquals(discounts, order.getDiscounts());
        assertEquals(observations, order.getObservations());
        assertEquals(BigDecimal.ZERO, order.getOriginalPrice());
        assertEquals(BigDecimal.ZERO, order.getTotalPrice());
        assertEquals(BigDecimal.ZERO, order.getDiscountAmount());
    }

    @Test
    @DisplayName("Should prepare an order")
    void shouldPrepareOrder() {
        CustomerId customerId = new CustomerId(UUID.randomUUID());
        List<OrderItem> orderItems = new ArrayList<>();
        List<Discount> discounts = new ArrayList<>();
        List<Observation> observations = new ArrayList<>();
        Order order = Order.receive(customerId, orderItems, discounts, observations);

        order.prepare();

        assertEquals(OrderStatus.IN_PREPARATION, order.getStatus());
    }

    @Test
    @DisplayName("Should calculate original price")
    void shouldCalculateOriginalPrice() {
        CustomerId customerId = new CustomerId(UUID.randomUUID());

        OrderItem item1 = new OrderItem(
            UUID.randomUUID(),
            "Product 1",
            1,
            new BigDecimal("10.00"),
            "Category 1",
            null
        );

        OrderItem item2 = new OrderItem(
            UUID.randomUUID(),
            "Product 2",
            1,
            new BigDecimal("20.00"),
            "Category 2",
            null
        );

        List<OrderItem> orderItems = List.of(item1, item2);
        List<Discount> discounts = new ArrayList<>();
        List<Observation> observations = new ArrayList<>();

        Order order = Order.receive(customerId, orderItems, discounts, observations);

        assertEquals(new BigDecimal("30.00"), order.getOriginalPrice());
    }

    @Test
    @DisplayName("Should calculate discount amount")
    void shouldCalculateDiscountAmount() {
        CustomerId customerId = new CustomerId(UUID.randomUUID());
        List<OrderItem> orderItems = new ArrayList<>();

        Discount discount1 = new Discount(new BigDecimal("5.00"));
        Discount discount2 = new Discount(new BigDecimal("3.00"));

        List<Discount> discounts = List.of(discount1, discount2);
        List<Observation> observations = new ArrayList<>();

        Order order = Order.receive(customerId, orderItems, discounts, observations);

        assertEquals(new BigDecimal("8.00"), order.getDiscountAmount());
    }

    @Test
    @DisplayName("Should calculate total price with discount")
    void shouldCalculateTotalPriceWithDiscount() {
        CustomerId customerId = new CustomerId(UUID.randomUUID());

        OrderItem item1 = new OrderItem(
            UUID.randomUUID(),
            "Product 1",
            1,
            new BigDecimal("10.00"),
            "Category 1",
            null
        );

        OrderItem item2 = new OrderItem(
            UUID.randomUUID(),
            "Product 2",
            1,
            new BigDecimal("20.00"),
            "Category 2",
            null
        );

        List<OrderItem> orderItems = List.of(item1, item2);

        Discount discount1 = new Discount(new BigDecimal("5.00"));
        Discount discount2 = new Discount(new BigDecimal("3.00"));

        List<Discount> discounts = List.of(discount1, discount2);
        List<Observation> observations = new ArrayList<>();

        Order order = Order.receive(customerId, orderItems, discounts, observations);

        assertEquals(new BigDecimal("30.00"), order.getOriginalPrice());
        assertEquals(new BigDecimal("8.00"), order.getDiscountAmount());
        assertEquals(new BigDecimal("22.00"), order.getTotalPrice());
    }
}