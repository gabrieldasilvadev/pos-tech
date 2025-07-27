package br.com.postech.soat.order.infrastructure.persistence;

import br.com.postech.soat.commons.application.Pagination;
import br.com.postech.soat.customer.domain.valueobject.CustomerId;
import br.com.postech.soat.order.domain.entity.Order;
import br.com.postech.soat.order.domain.entity.OrderItem;
import br.com.postech.soat.order.domain.entity.OrderStatus;
import br.com.postech.soat.order.infrastructure.persistence.entity.OrderEntity;
import br.com.postech.soat.order.infrastructure.persistence.entity.OrderItemEntity;
import br.com.postech.soat.order.infrastructure.persistence.jpa.OrderItemJpaRepository;
import br.com.postech.soat.order.infrastructure.persistence.jpa.OrderJpaRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

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
    @DisplayName("Should save order and order items when valid order is provided")
    void givenValidOrder_whenSave_thenReturnSavedOrder() {
        // Arrange
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

        // Act
        Order savedOrder = orderRepositoryImpl.save(order);

        // Assert
        assertNotNull(savedOrder);
        assertEquals(order.getId(), savedOrder.getId());
        assertEquals(1, savedOrder.getOrderItems().size());

        verify(orderJpaRepository, times(1)).save(any(OrderEntity.class));
        verify(orderItemJpaRepository, times(1)).saveAll(anyList());
    }

    @Test
    @DisplayName("Should return active orders when found")
    void givenActiveOrderStatusListAndPagination_whenFindActiveOrdersSorted_thenReturnOrdersList() {
        // Arrange
        Set<OrderStatus> activeOrderStatusList = Set.of(
            OrderStatus.DONE,
            OrderStatus.IN_PREPARATION,
            OrderStatus.RECEIVED
        );
        Pagination pagination = new Pagination(0, 10);

        OrderEntity orderEntity1 = buildOrderEntity(UUID.randomUUID(), OrderStatus.RECEIVED);
        OrderEntity orderEntity2 = buildOrderEntity(UUID.randomUUID(), OrderStatus.IN_PREPARATION);
        List<OrderEntity> orderEntities = List.of(orderEntity1, orderEntity2);

        OrderItemEntity orderItemEntity1 = buildOrderItemEntity(orderEntity1.getId(), "Product 1");
        OrderItemEntity orderItemEntity2 = buildOrderItemEntity(orderEntity2.getId(), "Product 2");

        when(orderJpaRepository.findActiveOrdersSorted(any(), any(Pageable.class))).thenReturn(orderEntities);
        when(orderItemJpaRepository.findByOrderId(orderEntity1.getId())).thenReturn(List.of(orderItemEntity1));
        when(orderItemJpaRepository.findByOrderId(orderEntity2.getId())).thenReturn(List.of(orderItemEntity2));

        // Act
        List<Order> result = orderRepositoryImpl.findActiveOrdersSorted(activeOrderStatusList, pagination);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(orderEntity1.getId(), result.get(0).getId().getValue());
        assertEquals(orderEntity2.getId(), result.get(1).getId().getValue());

        assertNotNull(result.get(0).getOrderItems());
        assertEquals(1, result.get(0).getOrderItems().size());
        assertEquals("Product 1", result.get(0).getOrderItems().getFirst().getName());

        assertNotNull(result.get(1).getOrderItems());
        assertEquals(1, result.get(1).getOrderItems().size());
        assertEquals("Product 2", result.get(1).getOrderItems().getFirst().getName());

        verify(orderJpaRepository, times(1)).findActiveOrdersSorted(any(), any(Pageable.class));
        verify(orderItemJpaRepository, times(1)).findByOrderId(orderEntity1.getId());
        verify(orderItemJpaRepository, times(1)).findByOrderId(orderEntity2.getId());
    }

    @Test
    @DisplayName("Should return empty list when no active orders found")
    void givenActiveOrderStatusListAndPagination_whenFindActiveOrdersSortedWithNoResults_thenReturnEmptyList() {
        // Arrange
        Set<OrderStatus> activeOrderStatusList = Set.of(
            OrderStatus.DONE,
            OrderStatus.IN_PREPARATION,
            OrderStatus.RECEIVED
        );
        Pagination pagination = new Pagination(0, 10);

        when(orderJpaRepository.findActiveOrdersSorted(any(), any(Pageable.class))).thenReturn(List.of());

        // Act
        List<Order> result = orderRepositoryImpl.findActiveOrdersSorted(activeOrderStatusList, pagination);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
        verify(orderJpaRepository, times(1)).findActiveOrdersSorted(any(), any(Pageable.class));
    }

    private OrderEntity buildOrderEntity(UUID orderId, OrderStatus status) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(orderId);
        orderEntity.setCustomerId(UUID.randomUUID());
        orderEntity.setStatus(status);
        orderEntity.setTotalPrice(new BigDecimal("25.00"));
        orderEntity.setDiscountAmount(BigDecimal.ZERO);
        orderEntity.setObservation("Test observation");
        return orderEntity;
    }

    private OrderItemEntity buildOrderItemEntity(UUID orderId, String productName) {
        OrderItemEntity orderItemEntity = new OrderItemEntity();
        orderItemEntity.setId(UUID.randomUUID());
        orderItemEntity.setOrderId(orderId);
        orderItemEntity.setProductId(UUID.randomUUID());
        orderItemEntity.setProductName(productName);
        orderItemEntity.setProductQuantity(1);
        orderItemEntity.setUnitPrice(new BigDecimal("15.50"));
        orderItemEntity.setDiscountAmount(BigDecimal.ZERO);
        return orderItemEntity;
    }
}