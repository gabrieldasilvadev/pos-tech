package br.com.postech.soat.payment.adapters.out;

import br.com.postech.soat.commons.infrastructure.aop.monitorable.Monitorable;
import br.com.postech.soat.payment.core.domain.model.Payment;
import br.com.postech.soat.payment.core.domain.model.PaymentId;
import br.com.postech.soat.payment.core.domain.model.PaymentStatus;
import br.com.postech.soat.payment.core.ports.out.PaymentRepository;
import br.com.postech.soat.payment.infrastructure.jpa.PaymentJpaRepository;
import br.com.postech.soat.payment.infrastructure.jpa.entity.PaymentEntity;
import br.com.postech.soat.payment.infrastructure.jpa.mapper.PaymentEntityMapper;
import java.util.List;
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

    @Override
    public Payment findById(PaymentId paymentId) {
        final PaymentEntity paymentEntity = paymentJpaRepository.findById(paymentId.getValue())
            .orElseThrow(() -> new RuntimeException("Payment not found"));
        return PaymentEntityMapper.INSTANCE.mapFrom(paymentEntity);
    }

    @Override
    public List<Payment> findPendingPayments() {
        List<PaymentEntity> paymentEntities = paymentJpaRepository.findByStatus(PaymentStatus.PENDING);
        return paymentEntities.stream()
            .map(PaymentEntityMapper.INSTANCE::mapFrom)
            .toList();
    }
}
