package br.com.postech.soat.order.core.application.handlers;

import br.com.postech.soat.customer.domain.valueobject.CustomerId;
import br.com.postech.soat.order.core.application.commands.CreateOrderCommand;
import br.com.postech.soat.order.core.domain.model.Discount;
import br.com.postech.soat.order.core.domain.model.Observation;
import br.com.postech.soat.order.core.domain.model.Order;
import br.com.postech.soat.order.core.domain.model.OrderItem;
import br.com.postech.soat.order.core.domain.model.OrderStatus;
import br.com.postech.soat.order.core.ports.out.OrderRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("CreateOrderCommandHandler Tests")
class CreateOrderCommandHandlerTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private CreateOrderCommandHandler createOrderCommandHandler;

    @Test
    @DisplayName("Should handle create order command successfully")
    void shouldHandleCreateOrderCommandSuccessfully() {
        CustomerId customerId = new CustomerId(UUID.randomUUID());

        OrderItem orderItem = new OrderItem(
            UUID.randomUUID(),
            "Test Product",
            1,
            new BigDecimal("10.00"),
            "Test Category",
            null
        );

        List<OrderItem> orderItems = List.of(orderItem);
        List<Discount> discounts = List.of(new Discount(new BigDecimal("2.00")));
        List<Observation> observations = List.of(new Observation("Test observation"));

        CreateOrderCommand command = new CreateOrderCommand(
            customerId,
            discounts,
            orderItems,
            observations
        );

        Order createdOrder = Order.receive(customerId, orderItems, discounts, observations);
        createdOrder.prepare();

        when(orderRepository.save(any(Order.class))).thenReturn(createdOrder);

        Order result = createOrderCommandHandler.handle(command);

        assertNotNull(result);
        assertEquals(OrderStatus.IN_PREPARATION, result.getStatus());
        assertEquals(customerId, result.getCustomerId());
        assertEquals(orderItems, result.getOrderItems());
        assertEquals(discounts, result.getDiscounts());
        assertEquals(observations, result.getObservations());
        assertEquals(new BigDecimal("10.00"), result.getOriginalPrice());
        assertEquals(new BigDecimal("2.00"), result.getDiscountAmount());
        assertEquals(new BigDecimal("8.00"), result.getTotalPrice());

        verify(orderRepository).save(any(Order.class));
    }

    @Test
    @DisplayName("Should handle create order command with exception")
    void shouldHandleCreateOrderCommandWithException() {
        CustomerId customerId = new CustomerId(UUID.randomUUID());
        List<OrderItem> orderItems = new ArrayList<>();
        List<Discount> discounts = new ArrayList<>();
        List<Observation> observations = new ArrayList<>();

        CreateOrderCommand command = new CreateOrderCommand(
            customerId,
            discounts,
            orderItems,
            observations
        );

        when(orderRepository.save(any(Order.class))).thenThrow(new RuntimeException("Test exception"));

        try {
            createOrderCommandHandler.handle(command);
        } catch (Exception e) {
            assertEquals("Test exception", e.getMessage());
        }

        verify(orderRepository).save(any(Order.class));
    }
}