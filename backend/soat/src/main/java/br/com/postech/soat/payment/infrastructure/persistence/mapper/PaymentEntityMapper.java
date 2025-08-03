package br.com.postech.soat.payment.infrastructure.persistence.mapper;

import br.com.postech.soat.payment.domain.entity.Payment;
import br.com.postech.soat.payment.infrastructure.persistence.entity.PaymentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PaymentEntityMapper {

    PaymentEntityMapper INSTANCE = Mappers.getMapper(PaymentEntityMapper.class);

    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "orderId", source = "orderId.value")
    @Mapping(target = "customerId", source = "customerId.value")
    @Mapping(target = "method", source = "method")
    @Mapping(target = "status", source = "status")
    PaymentEntity mapFrom(Payment payment);

    @Mapping(target = "paymentId", source = "id")
    @Mapping(target = "orderId.value", source = "orderId")
    @Mapping(target = "customerId.value", source = "customerId")
    @Mapping(target = "method", source = "method")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "processedAt", source = "processedAt")
    Payment mapFrom(PaymentEntity paymentEntity);
}
