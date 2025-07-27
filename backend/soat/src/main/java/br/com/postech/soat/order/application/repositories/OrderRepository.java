package br.com.postech.soat.order.application.repositories;

import br.com.postech.soat.commons.application.Pagination;
import br.com.postech.soat.order.domain.entity.Order;
import br.com.postech.soat.order.domain.entity.OrderStatus;
import java.util.List;
import java.util.Set;

public interface OrderRepository {
    
    Order save(Order order);
    
    List<Order> findActiveOrdersSorted(Set<OrderStatus> orderStatuses, Pagination pagination);
}
