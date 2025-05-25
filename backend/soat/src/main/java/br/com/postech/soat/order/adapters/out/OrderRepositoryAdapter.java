package br.com.postech.soat.order.adapters.out;

import br.com.postech.soat.commons.infrastructure.aop.monitorable.Monitorable;
import br.com.postech.soat.order.adapters.in.http.controller.mapper.OrderItemMapper;
import br.com.postech.soat.order.core.domain.model.Order;
import br.com.postech.soat.order.core.ports.out.OrderRepository;
import br.com.postech.soat.order.infrastructure.jpa.OrderItemJpaRepository;
import br.com.postech.soat.order.infrastructure.jpa.OrderJpaRepository;
import br.com.postech.soat.order.infrastructure.jpa.entity.OrderEntity;
import br.com.postech.soat.order.infrastructure.jpa.entity.OrderItemEntity;
import br.com.postech.soat.order.infrastructure.jpa.mapper.OrderEntityMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Monitorable
public class OrderRepositoryAdapter implements OrderRepository {
    private final OrderJpaRepository orderJpaRepository;
    private final OrderItemJpaRepository orderItemJpaRepository;

    private final Logger logger = LoggerFactory.getLogger(OrderRepositoryAdapter.class);

    @Override
    public Order save(Order order) {
        OrderEntity orderEntity = OrderEntityMapper.INSTANCE.toEntity(order);
        orderJpaRepository.save(orderEntity);
        logger.info("Order saved : {}", orderEntity);

        List<OrderItemEntity> orderItemEntities = order.getOrderItems()
            .stream()
            .map(orderItem -> OrderItemMapper.INSTANCE.mapFrom(orderItem, order.getId().getValue()))
            .toList();

        List<OrderItemEntity> orderItemEntitiesSaved = orderItemJpaRepository.saveAll(orderItemEntities);
        logger.info("Quantity order items saved : {}", orderItemEntitiesSaved.size());
        return order;
    }
}
