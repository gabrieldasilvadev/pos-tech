package br.com.postech.soat.order.adapters.out;

import br.com.postech.soat.commons.infrastructure.aop.monitorable.Monitorable;
import br.com.postech.soat.order.core.domain.model.Order;
import br.com.postech.soat.order.core.ports.out.OrderRepository;
import br.com.postech.soat.order.infrastructure.jpa.OrderJpaRepository;
import br.com.postech.soat.order.infrastructure.jpa.entity.OrderEntity;
import br.com.postech.soat.order.infrastructure.jpa.mapper.OrderEntityMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Monitorable
public class OrderRepositoryAdapter implements OrderRepository {
    private final OrderJpaRepository orderJpaRepository;

    private final Logger logger = LoggerFactory.getLogger(OrderRepositoryAdapter.class);

    @Override
    public Order sendOrder(Order order) {
        order.prepare();
        OrderEntity orderEntity = OrderEntityMapper.INSTANCE.mapFrom(order);
        orderJpaRepository.save(orderEntity);
        logger.info("Order saved : {}", orderEntity);
        return order;
    }
}
