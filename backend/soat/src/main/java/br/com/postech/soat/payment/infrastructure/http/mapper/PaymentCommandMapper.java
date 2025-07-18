package br.com.postech.soat.payment.infrastructure.http.mapper;

import br.com.postech.soat.openapi.model.PostPaymentsRequestDto;
import br.com.postech.soat.payment.application.command.InitiatePaymentCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PaymentCommandMapper {
    PaymentCommandMapper INSTANCE = Mappers.getMapper(PaymentCommandMapper.class);

    @Mapping(target = "orderId", expression = "java(new OrderId(postPaymentsRequestDto.getOrderId()))")
    @Mapping(target = "customerId", expression = "java(new CustomerId(postPaymentsRequestDto.getCustomerId()))")
    @Mapping(target = "paymentMethod", source = "postPaymentsRequestDto.paymentMethod.value")
    @Mapping(target = "amount", source = "postPaymentsRequestDto.amount")
    InitiatePaymentCommand toCommand(PostPaymentsRequestDto postPaymentsRequestDto);
}
