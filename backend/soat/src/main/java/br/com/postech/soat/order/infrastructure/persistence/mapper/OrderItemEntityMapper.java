package br.com.postech.soat.order.infrastructure.persistence.mapper;

import br.com.postech.soat.order.domain.entity.OrderItem;
import br.com.postech.soat.order.domain.entity.OrderItemId;
import br.com.postech.soat.order.domain.vo.Discount;
import br.com.postech.soat.order.infrastructure.persistence.entity.OrderItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface OrderItemEntityMapper {
    OrderItemEntityMapper INSTANCE = Mappers.getMapper(OrderItemEntityMapper.class);

    default OrderItem toDomain(OrderItemEntity orderItemEntity) {
        if (orderItemEntity == null) {
            return null;
        }
        
        Discount discount = new Discount(orderItemEntity.getDiscountAmount());
        
        return new OrderItem(
            orderItemEntity.getProductId(),
            orderItemEntity.getProductName(),
            orderItemEntity.getProductQuantity(),
            orderItemEntity.getUnitPrice(),
            null, //productCategory
            discount
        );
    }

    default List<OrderItem> toDomainList(List<OrderItemEntity> orderItemEntities) {
        if (orderItemEntities == null) {
            return null;
        }
        
        return orderItemEntities.stream()
            .map(this::toDomain)
            .toList();
    }
}