package br.com.postech.soat.order.adapters.in.http.controller.mapper;

import br.com.postech.soat.openapi.model.OrderItemDto;
import br.com.postech.soat.order.core.domain.model.OrderItem;
import java.math.BigDecimal;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(
    uses = {DiscountMapper.class},
    imports = {BigDecimal.class, DiscountMapper.class}
)
public interface OrderItemMapper {

    OrderItemMapper INSTANCE = Mappers.getMapper(OrderItemMapper.class);

    @Mapping(target = "price", expression = "java(new BigDecimal(orderItemDto.getPrice()))")
    @Mapping(target = "discount", source = "discount")
    @Mapping(target = "category", expression = "java(orderItemDto.getCategory() != null ? orderItemDto.getCategory().getValue() : null)")
    OrderItem mapFrom(OrderItemDto orderItemDto);

    default List<OrderItem> mapFrom(List<OrderItemDto> orderItemDtos) {
        if (orderItemDtos == null) {
            return null;
        }
        return orderItemDtos.stream()
            .map(this::mapFrom)
            .toList();
    }
}
