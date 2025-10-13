package br.com.postech.soat.order.infrastructure.http.mapper;

import br.com.postech.soat.openapi.model.PostOrdersRequestDto;
import br.com.postech.soat.order.application.command.CreateOrderCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = {
        NoteMapper.class,
        OrderItemMapper.class,
        DiscountMapper.class
    })
public interface CreateOrderCommandMapper {

    CreateOrderCommandMapper INSTANCE = Mappers.getMapper(CreateOrderCommandMapper.class);

    @Mapping(target = "customerId", expression = "java(new CustomerId(request.getCustomerId()))")
    @Mapping(target = "observations", expression = "java(NoteMapper.INSTANCE.mapFrom(request.getNotes()))")
    @Mapping(target = "orderItems", source = "request.items")
    @Mapping(target = "discounts", source = "request.discounts")
    CreateOrderCommand mapFrom(PostOrdersRequestDto request);
}
