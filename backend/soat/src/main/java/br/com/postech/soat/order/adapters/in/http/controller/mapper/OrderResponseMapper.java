package br.com.postech.soat.order.adapters.in.http.controller.mapper;

import br.com.postech.soat.openapi.model.CategoryDto;
import br.com.postech.soat.openapi.model.DiscountDto;
import br.com.postech.soat.openapi.model.OrderItemDto;
import br.com.postech.soat.openapi.model.OrderStatusDto;
import br.com.postech.soat.openapi.model.PostOrders201ResponseDiscountsInnerDto;
import br.com.postech.soat.openapi.model.PostOrders201ResponseDto;
import br.com.postech.soat.order.core.domain.model.Discount;
import br.com.postech.soat.order.core.domain.model.Order;
import br.com.postech.soat.order.core.domain.model.OrderItem;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(imports = {OrderStatusDto.class, CategoryDto.class})
public interface OrderResponseMapper {
    OrderResponseMapper INSTANCE = Mappers.getMapper(OrderResponseMapper.class);

    @Mapping(target = "orderId", source = "id.value")
    @Mapping(target = "status", expression = "java(OrderStatusDto.fromValue(order.getStatus().name()))")
    @Mapping(target = "items", source = "orderItems")
    @Mapping(target = "discounts", source = "discounts")
    @Mapping(target = "discountAmountTotal", expression = "java(order.getDiscountAmount().toString())")
    @Mapping(target = "subtotal", expression = "java(order.getOriginalPrice().toString())")
    @Mapping(target = "total", expression = "java(order.getTotalPrice().toString())")
    PostOrders201ResponseDto toResponse(Order order);

    @Mapping(target = "productId", source = "productId")
    @Mapping(target = "discount", source = "discount")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "category", expression = "java(CategoryDto.fromValue(orderItem.getCategory()))")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "price", expression = "java(orderItem.getPrice().toString())")
    OrderItemDto toOrderItemDto(OrderItem orderItem);

    default DiscountDto toDiscountDto(Discount discount) {
        return DiscountDto.builder()
            .id(UUID.randomUUID())
            .value(discount.value().toString())
            .build();
    }

    default PostOrders201ResponseDiscountsInnerDto toDiscountInnerDto(Discount discount) {
        return PostOrders201ResponseDiscountsInnerDto.builder()
            .discountId(discount.id().value())
            .amount(discount.value().toString())
            .build();
    }
}