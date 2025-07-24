package br.com.postech.soat.order.application.usecases;

import br.com.postech.soat.commons.application.Pagination;
import br.com.postech.soat.order.application.repositories.OrderRepository;
import br.com.postech.soat.order.domain.entity.Order;
import br.com.postech.soat.order.domain.entity.OrderStatus;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListActiveOrdersUseCaseTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private ListActiveOrdersUseCase listActiveOrdersUseCase;
    
    private final Set<OrderStatus> activeOrderStatusList = OrderStatus.activeOrderStatusList();

    @Test
    @DisplayName("Should return active orders sorted by priority and creation date")
    void givenActiveOrders_whenExecute_thenReturnOrdersSorted() {
        // Arrange
        Pagination pagination = new Pagination(0, 10);
        List<Order> expectedOrders = createMockOrders();
        when(orderRepository.findActiveOrdersSorted(activeOrderStatusList, pagination)).thenReturn(expectedOrders);

        // Act
        List<Order> result = listActiveOrdersUseCase.execute(pagination);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(orderRepository).findActiveOrdersSorted(activeOrderStatusList, pagination);
    }

    @Test
    @DisplayName("Should return empty list when no active orders found")
    void givenNoActiveOrders_whenExecute_thenReturnEmptyList() {
        // Arrange
        Pagination pagination = new Pagination(0, 10);
        when(orderRepository.findActiveOrdersSorted(activeOrderStatusList, pagination)).thenReturn(List.of());

        // Act
        List<Order> result = listActiveOrdersUseCase.execute(pagination);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(orderRepository).findActiveOrdersSorted(activeOrderStatusList, pagination);
    }

    @Test
    @DisplayName("Should handle different pagination parameters correctly")
    void givenDifferentPaginationParams_whenExecute_thenPassCorrectParametersToRepository() {
        // Arrange
        Pagination pagination1 = new Pagination(1, 5);
        Pagination pagination2 = new Pagination(2, 20);
        List<Order> expectedOrders = createMockOrders();
        
        when(orderRepository.findActiveOrdersSorted(activeOrderStatusList, pagination1)).thenReturn(expectedOrders);
        when(orderRepository.findActiveOrdersSorted(activeOrderStatusList, pagination2)).thenReturn(List.of());

        // Act
        List<Order> result1 = listActiveOrdersUseCase.execute(pagination1);
        List<Order> result2 = listActiveOrdersUseCase.execute(pagination2);

        // Assert
        assertEquals(2, result1.size());
        assertEquals(0, result2.size());
        verify(orderRepository).findActiveOrdersSorted(activeOrderStatusList, pagination1);
        verify(orderRepository).findActiveOrdersSorted(activeOrderStatusList, pagination2);
    }

    private List<Order> createMockOrders() {
        Order order1 = mock(Order.class);
        Order order2 = mock(Order.class);

        return Arrays.asList(order1, order2);
    }
}