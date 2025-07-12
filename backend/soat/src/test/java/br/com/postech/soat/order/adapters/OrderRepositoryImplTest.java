package br.com.postech.soat.order.adapters;

import br.com.postech.soat.customer.domain.valueobject.CustomerId;
import br.com.postech.soat.order.domain.entity.Order;
import br.com.postech.soat.order.domain.entity.OrderItem;
import br.com.postech.soat.order.infrastructure.persistence.OrderRepositoryImpl;
import br.com.postech.soat.order.infrastructure.persistence.jpa.OrderItemJpaRepository;
import br.com.postech.soat.order.infrastructure.persistence.jpa.OrderJpaRepository;
import br.com.postech.soat.order.infrastructure.persistence.entity.OrderEntity;
import br.com.postech.soat.order.infrastructure.persistence.entity.OrderItemEntity;
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
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("OrderRepositoryAdapter Tests")
class OrderRepositoryImplTest {

    @Mock
    private OrderJpaRepository orderJpaRepository;

    @Mock
    private OrderItemJpaRepository orderItemJpaRepository;

    @InjectMocks
    private OrderRepositoryImpl orderRepositoryImpl;

    @Test
    @DisplayName("Should save order and order items")
    void shouldSaveOrderAndOrderItems() {
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
        Order order = Order.receive(customerId, orderItems, new ArrayList<>(), new ArrayList<>());

        OrderEntity savedOrderEntity = new OrderEntity();
        savedOrderEntity.setId(order.getId().getValue());

        OrderItemEntity savedOrderItemEntity = new OrderItemEntity();
        savedOrderItemEntity.setId(orderItem.getId().getValue());

        when(orderJpaRepository.save(any(OrderEntity.class))).thenReturn(savedOrderEntity);
        when(orderItemJpaRepository.saveAll(anyList())).thenReturn(List.of(savedOrderItemEntity));

        Order savedOrder = orderRepositoryImpl.save(order);

        assertNotNull(savedOrder);
        assertEquals(order.getId(), savedOrder.getId());
        assertEquals(1, savedOrder.getOrderItems().size());

        verify(orderJpaRepository, times(1)).save(any(OrderEntity.class));
        verify(orderItemJpaRepository, times(1)).saveAll(anyList());
    }
}