package br.com.postech.soat.payment.infrastructure.persistence;

import br.com.postech.soat.commons.infrastructure.aop.monitorable.Monitorable;
import br.com.postech.soat.commons.infrastructure.exception.NotFoundException;
import br.com.postech.soat.payment.domain.entity.Payment;
import br.com.postech.soat.payment.domain.valueobject.PaymentId;
import br.com.postech.soat.payment.domain.entity.PaymentStatus;
import br.com.postech.soat.payment.application.repositories.PaymentRepository;
import br.com.postech.soat.payment.infrastructure.persistence.entity.PaymentEntity;
import br.com.postech.soat.payment.infrastructure.persistence.jpa.PaymentJpaRepository;
import br.com.postech.soat.payment.infrastructure.persistence.mapper.PaymentEntityMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Monitorable
public class PaymentRepositoryImpl implements PaymentRepository {
    private final PaymentJpaRepository paymentJpaRepository;
    private final Logger logger = LoggerFactory.getLogger(PaymentRepositoryImpl.class);

    @Override
    public void save(Payment payment) {
        final PaymentEntity paymentEntity = PaymentEntityMapper.INSTANCE.mapFrom(payment);
        paymentJpaRepository.save(paymentEntity);
        logger.info("Payment saved with ID: {}", payment.getId());
    }

    @Override
    public Payment findById(PaymentId paymentId) {
        final PaymentEntity paymentEntity = paymentJpaRepository.findById(paymentId.getValue())
            .orElseThrow(() -> new NotFoundException("Payment not found"));
        logger.info("Payment found with ID: {}", paymentId);
        return PaymentEntityMapper.INSTANCE.mapFrom(paymentEntity);
    }

    @Override
    public List<Payment> findPendingPayments() {
        List<PaymentEntity> paymentEntities = paymentJpaRepository.findByStatus(PaymentStatus.PENDING);
        logger.info("Found {} pending payments", paymentEntities.size());
        return paymentEntities.stream()
            .map(PaymentEntityMapper.INSTANCE::mapFrom)
            .toList();
    }
}
