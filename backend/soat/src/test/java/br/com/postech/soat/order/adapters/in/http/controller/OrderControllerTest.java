package br.com.postech.soat.order.adapters.in.http.controller;

import br.com.postech.soat.commons.application.mediator.Mediator;
import br.com.postech.soat.customer.core.domain.valueobject.CustomerId;
import br.com.postech.soat.openapi.model.CategoryDto;
import br.com.postech.soat.openapi.model.OrderItemDto;
import br.com.postech.soat.openapi.model.PostOrders201ResponseDto;
import br.com.postech.soat.openapi.model.PostOrdersRequestDto;
import br.com.postech.soat.order.core.application.commands.CreateOrderCommand;
import br.com.postech.soat.order.core.domain.model.Discount;
import br.com.postech.soat.order.core.domain.model.Order;
import br.com.postech.soat.order.core.domain.model.OrderItem;
import br.com.postech.soat.order.core.domain.model.OrderStatus;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("OrderController Tests")
class OrderControllerTest {

    @Mock
    private Mediator mediator;

    @InjectMocks
    private OrderController orderController;

    @Test
    @DisplayName("Should create order successfully")
    void shouldCreateOrderSuccessfully() {
        UUID customerId = UUID.randomUUID();
        PostOrdersRequestDto request = new PostOrdersRequestDto();
        request.setCustomerId(customerId);

        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setProductId(UUID.randomUUID());
        orderItemDto.setName("Test Product");
        orderItemDto.setQuantity(1);
        orderItemDto.setPrice(10.00);
        orderItemDto.setCategory(CategoryDto.SNACK);

        request.setItems(List.of(orderItemDto));

        Discount discount = new Discount(BigDecimal.ZERO);
        OrderItem orderItem = new OrderItem(
            orderItemDto.getProductId(),
            orderItemDto.getName(),
            orderItemDto.getQuantity(),
            new BigDecimal(orderItemDto.getPrice()),
            orderItemDto.getCategory().getValue(),
            discount
        );

        Order createdOrder = Order.receive(
            new CustomerId(customerId),
            List.of(orderItem),
            new ArrayList<>(),
            new ArrayList<>()
        );
        createdOrder.prepare();

        when(mediator.send(any(CreateOrderCommand.class))).thenReturn(createdOrder);

        ResponseEntity<PostOrders201ResponseDto> response = orderController.postOrders(request);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(createdOrder.getId().getValue(), response.getBody().getOrderId());
        assertEquals(OrderStatus.IN_PREPARATION.name(), response.getBody().getStatus().name());
        assertEquals(1, response.getBody().getItems().size());
        assertEquals(orderItemDto.getProductId(), response.getBody().getItems().get(0).getProductId());
        assertEquals(orderItemDto.getName(), response.getBody().getItems().get(0).getName());
        assertEquals(orderItemDto.getQuantity(), response.getBody().getItems().get(0).getQuantity());
        assertEquals(orderItemDto.getPrice(), response.getBody().getItems().get(0).getPrice());
        assertEquals(orderItemDto.getCategory(), response.getBody().getItems().get(0).getCategory());
    }
}
