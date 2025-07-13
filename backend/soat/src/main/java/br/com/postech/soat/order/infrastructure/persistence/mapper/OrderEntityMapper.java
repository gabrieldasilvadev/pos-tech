package br.com.postech.soat.order.infrastructure.persistence.mapper;

import br.com.postech.soat.customer.core.domain.model.CustomerId;
import br.com.postech.soat.order.domain.entity.Order;
import br.com.postech.soat.order.domain.entity.OrderId;
import br.com.postech.soat.order.domain.vo.Observation;
import br.com.postech.soat.order.infrastructure.persistence.entity.OrderEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface OrderEntityMapper {
    OrderEntityMapper INSTANCE = Mappers.getMapper(OrderEntityMapper.class);

    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "customerId", source = "customerId.value")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "totalPrice", source = "totalPrice")
    @Mapping(target = "discountAmount", source = "discountAmount")
    @Mapping(target = "observation", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    OrderEntity toEntity(Order order);

    @AfterMapping
    default void mapObservations(Order order, @MappingTarget OrderEntity orderEntity) {
        orderEntity.setObservation(order.getObservations().stream()
            .map(Observation::getText)
            .collect(Collectors.joining(",")));
    }

    default Order toDomain(OrderEntity orderEntity) {
        OrderId orderId = new OrderId(orderEntity.getId());
        CustomerId customerId = new CustomerId(orderEntity.getCustomerId());
        List<Observation> observations = orderEntity.getObservation() != null 
            ? Arrays.stream(orderEntity.getObservation().split(","))
                .map(Observation::new)
                .collect(Collectors.toList())
            : List.of();
        
        return new Order(orderId, customerId, orderEntity.getStatus(), 
                        orderEntity.getTotalPrice(), orderEntity.getDiscountAmount(), observations);
    }
}
