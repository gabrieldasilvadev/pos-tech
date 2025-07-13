package br.com.postech.soat.order.application.repositories;

import br.com.postech.soat.order.domain.entity.Order;

public interface OrderRepository {
    Order save(Order order);
}
