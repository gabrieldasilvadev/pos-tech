package br.com.postech.soat.order.application.repositories;

import br.com.postech.soat.order.domain.entity.Order;
import br.com.postech.soat.order.domain.entity.OrderStatus;
import java.util.List;

public interface OrderRepository {
    
    Order save(Order order);
    
    List<Order> findActiveOrdersSorted(List<OrderStatus> orderStatuses);
}
