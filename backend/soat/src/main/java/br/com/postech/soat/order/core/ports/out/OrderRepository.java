package br.com.postech.soat.order.core.ports.out;

import br.com.postech.soat.order.core.domain.model.Order;

public interface OrderRepository {
    Order sendOrder(Order order);
}
