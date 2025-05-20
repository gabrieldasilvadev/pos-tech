package br.com.postech.soat.order.infrastructure.jpa.mapper;

import br.com.postech.soat.order.core.domain.model.Observation;
import br.com.postech.soat.order.core.domain.model.Order;
import br.com.postech.soat.order.infrastructure.jpa.entity.OrderEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderEntityMapper {
    OrderEntityMapper INSTANCE = Mappers.getMapper(OrderEntityMapper.class);

    @Mapping(target = "id", source = "id.id")
    @Mapping(target = "customerId", source = "customerId.value")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "totalPrice", source = "totalPrice")
    @Mapping(target = "discountAmount", source = "discountAmount")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    OrderEntity mapFrom(Order order);

    @AfterMapping
    default void mapObservations(Order order, @MappingTarget OrderEntity orderEntity) {
        orderEntity.setObservation(order.getObservations().stream()
                .map(Observation::text)
                .collect(java.util.stream.Collectors.joining(",")));
    }
}
