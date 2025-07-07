package br.com.postech.soat.order.infrastructure.persistence;

import br.com.postech.soat.commons.infrastructure.aop.monitorable.Monitorable;
import br.com.postech.soat.order.infrastructure.http.mapper.OrderItemMapper;
import br.com.postech.soat.order.domain.entity.Order;
import br.com.postech.soat.order.application.repositories.OrderRepository;
import br.com.postech.soat.order.infrastructure.persistence.jpa.OrderItemJpaRepository;
import br.com.postech.soat.order.infrastructure.persistence.jpa.OrderJpaRepository;
import br.com.postech.soat.order.infrastructure.persistence.entity.OrderEntity;
import br.com.postech.soat.order.infrastructure.persistence.entity.OrderItemEntity;
import br.com.postech.soat.order.infrastructure.persistence.mapper.OrderEntityMapper;
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
