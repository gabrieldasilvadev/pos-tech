package br.com.postech.soat.order.application.usecases;

import br.com.postech.soat.order.application.repositories.OrderRepository;
import br.com.postech.soat.order.domain.entity.Order;
import br.com.postech.soat.order.domain.entity.OrderStatus;
import java.util.Arrays;
import java.util.List;
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
    
    private final List<OrderStatus> activeOrderStatusList = OrderStatus.activeOrderStatusList();

    @Test
    @DisplayName("Should return active orders sorted by priority and creation date")
    void givenActiveOrders_whenExecute_thenReturnOrdersSorted() {
        List<Order> expectedOrders = createMockOrders();
        when(orderRepository.findActiveOrdersSorted(activeOrderStatusList)).thenReturn(expectedOrders);

        List<Order> result = listActiveOrdersUseCase.execute();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(orderRepository).findActiveOrdersSorted(activeOrderStatusList);
    }

    @Test
    @DisplayName("Should return empty list when no active orders found")
    void givenNoActiveOrders_whenExecute_thenReturnEmptyList() {
        when(orderRepository.findActiveOrdersSorted(activeOrderStatusList)).thenReturn(List.of());

        List<Order> result = listActiveOrdersUseCase.execute();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(orderRepository).findActiveOrdersSorted(activeOrderStatusList);
    }

    private List<Order> createMockOrders() {
        Order order1 = mock(Order.class);
        Order order2 = mock(Order.class);

        return Arrays.asList(order1, order2);
    }
}