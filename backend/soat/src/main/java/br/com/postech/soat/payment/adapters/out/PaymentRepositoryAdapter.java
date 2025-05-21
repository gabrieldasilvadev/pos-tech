package br.com.postech.soat.payment.adapters.out;

import br.com.postech.soat.commons.infrastructure.aop.monitorable.Monitorable;
import br.com.postech.soat.payment.core.domain.model.Payment;
import br.com.postech.soat.payment.core.ports.out.PaymentRepository;
import br.com.postech.soat.payment.infrastructure.jpa.PaymentJpaRepository;
import br.com.postech.soat.payment.infrastructure.jpa.entity.PaymentEntity;
import br.com.postech.soat.payment.infrastructure.jpa.mapper.PaymentEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Monitorable
public class PaymentRepositoryAdapter implements PaymentRepository {
    private final PaymentJpaRepository paymentJpaRepository;

    @Override
    public void save(Payment payment) {
        final PaymentEntity paymentEntity = PaymentEntityMapper.INSTANCE.mapFrom(payment);
        paymentJpaRepository.save(paymentEntity);
    }
}
