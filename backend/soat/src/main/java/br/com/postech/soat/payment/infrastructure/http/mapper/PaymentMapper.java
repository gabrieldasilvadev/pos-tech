package br.com.postech.soat.payment.infrastructure.http.mapper;

import br.com.postech.soat.openapi.model.GetPaymentsPaymentId200ResponseDto;
import br.com.postech.soat.openapi.model.PaymentStatusDto;
import br.com.postech.soat.payment.domain.entity.Payment;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(
    imports = {PaymentStatusDto.class}
)
public interface PaymentMapper {
    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    @Mapping(target = "paymentId", source = "payment.id.value")
    @Mapping(target = "orderId", source = "payment.orderId.value")
    @Mapping(target = "amount", source = "payment.amount")
    @Mapping(target = "paymentStatus", expression = "java(PaymentStatusDto.fromValue(payment.getStatus().name()))")
    @Mapping(target = "processedAt", source = "payment.processedAt")
    GetPaymentsPaymentId200ResponseDto toResponse(Payment payment);

    default OffsetDateTime map(Instant value) {
        return value == null ? null : OffsetDateTime.ofInstant(value, ZoneOffset.UTC);
    }
}
