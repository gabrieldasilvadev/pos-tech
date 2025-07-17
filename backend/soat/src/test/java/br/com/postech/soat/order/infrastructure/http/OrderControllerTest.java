package br.com.postech.soat.order.infrastructure.http;

import br.com.postech.soat.customer.core.domain.model.CustomerId;
import br.com.postech.soat.openapi.model.GetOrders200ResponseInnerDto;
import br.com.postech.soat.order.application.repositories.OrderRepository;
import br.com.postech.soat.order.application.usecases.CreateOrderUseCase;
import br.com.postech.soat.order.domain.entity.Order;
import br.com.postech.soat.order.domain.entity.OrderItem;
import br.com.postech.soat.order.domain.entity.OrderStatus;
import br.com.postech.soat.order.domain.vo.Discount;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("OrderController Tests")
class OrderControllerTest {

    @Mock
    private CreateOrderUseCase createOrderUseCase;

    @Mock
    private OrderRepository orderRepository;

    private OrderController orderController;

    @BeforeEach
    void setUp() {
        orderController = new OrderController(createOrderUseCase, orderRepository);
    }

    @Test
    @DisplayName("Should return active orders when orders are found")
    void givenActiveOrders_whenGetOrders_thenReturnOrdersList() {
        CustomerId customerId = new CustomerId(UUID.randomUUID());
        Discount discount = new Discount(BigDecimal.ZERO);
        OrderItem orderItem = new OrderItem(
            UUID.randomUUID(),
            "Test Product",
            2,
            new BigDecimal("15.50"),
            "SNACK",
            discount
        );

        List<OrderItem> orderItems = List.of(orderItem);
        Order order = Order.receive(customerId, orderItems, new ArrayList<>(), new ArrayList<>());
        List<Order> orders = List.of(order);

        when(orderRepository.findActiveOrdersSorted(OrderStatus.activeOrderStatusList())).thenReturn(orders);

        ResponseEntity<List<GetOrders200ResponseInnerDto>> response = orderController.getOrders();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());

        GetOrders200ResponseInnerDto responseDto = response.getBody().getFirst();

        assertEquals(order.getId().getValue(), responseDto.getOrderId());
        assertEquals(order.getCustomerId().value(), responseDto.getCustomerId());
        assertEquals(order.getTotalPrice().doubleValue(), responseDto.getTotal());
        assertEquals(order.getDiscountAmount().doubleValue(), responseDto.getDiscountAmountTotal());
        assertNotNull(responseDto.getItems());
        assertEquals(1, responseDto.getItems().size());
    }

    @Test
    @DisplayName("Should return no content when no active orders are found")
    void givenNoActiveOrders_whenGetOrders_thenReturnNoContent() {
        when(orderRepository.findActiveOrdersSorted(OrderStatus.activeOrderStatusList())).thenReturn(List.of());

        ResponseEntity<List<GetOrders200ResponseInnerDto>> response = orderController.getOrders();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}